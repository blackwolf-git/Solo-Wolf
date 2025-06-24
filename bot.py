import os
import logging
from pathlib import Path
from git import Repo
from telegram import Update
from telegram.ext import ApplicationBuilder, CommandHandler, MessageHandler, ContextTypes, filters
from llama_cpp import Llama

# إعدادات البوت
TELEGRAM_BOT_TOKEN = "7609338374:AAFpjJxbY2RWBUEr7DGPR0VKcJdZpg5c2U8"
MODEL_PATH = "zephyr-7b-beta.Q4_K_M.gguf"  # يجب تحميله مسبقاً

# إعدادات المسارات
REPOS_DIR = Path("./repos")
REPOS_DIR.mkdir(exist_ok=True)

# تحميل النموذج (يتم مرة واحدة عند التشغيل)
llm = Llama(
    model_path=MODEL_PATH,
    n_ctx=2048,
    n_threads=4,
    n_gpu_layers=33  # ضبط حسب GPU المتاح
)

# تخزين بيانات المستخدمين
user_sessions = {}

# إعداد السجل
logging.basicConfig(
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s",
    level=logging.INFO
)
logger = logging.getLogger(__name__)

async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """بدء المحادثة مع المستخدم"""
    user_id = update.effective_user.id
    user_sessions[user_id] = {"state": "awaiting_token"}
    
    await update.message.reply_text(
        "مرحباً! أنا بوت تحليل مستودعات GitHub الذكي.\n\n"
        "🔐 يرجى إرسال توكن GitHub الخاص بك (سيتم استخدامه للوصول إلى المستودع فقط):"
    )

async def handle_message(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """معالجة الرسائل الواردة من المستخدم"""
    user_id = update.effective_user.id
    message_text = update.message.text.strip()
    
    if user_id not in user_sessions:
        await start(update, context)
        return
    
    session = user_sessions[user_id]
    
    if session["state"] == "awaiting_token":
        # تخزين التوكن والانتظار لرابط المستودع
        session["github_token"] = message_text
        session["state"] = "awaiting_repo"
        await update.message.reply_text(
            "✅ تم حفظ توكن GitHub.\n\n"
            "📦 يرجى إرسال رابط المستودع على GitHub (مثال: https://github.com/username/repo):"
        )
    
    elif session["state"] == "awaiting_repo":
        # معالجة المستودع
        repo_url = message_text
        session["repo_url"] = repo_url
        session["state"] = "processing"
        
        await update.message.reply_text("⏳ جاري تحميل المستودع وتحليله... قد تستغرق العملية بضع دقائق.")
        
        try:
            repo_name = repo_url.split("/")[-1].replace(".git", "")
            clone_path = REPOS_DIR / f"{user_id}_{repo_name}"
            
            # استنساخ المستودع
            if clone_path.exists():
                await update.message.reply_text("⚠️ يوجد بالفعل مستودع بنفس الاسم، جاري تحديثه...")
                repo = Repo(clone_path)
                repo.remotes.origin.pull()
            else:
                clone_url = repo_url.replace("https://", f"https://{session['github_token']}@")
                Repo.clone_from(clone_url, clone_path)
            
            # تحليل الكود
            analysis_result = await analyze_repository(clone_path)
            
            # إرسال النتائج
            await update.message.reply_text(f"🔍 نتائج التحليل:\n\n{analysis_result}")
            
        except Exception as e:
            logger.error(f"Error processing repository: {str(e)}")
            await update.message.reply_text(f"❌ حدث خطأ أثناء معالجة المستودع:\n{str(e)}")
        
        finally:
            # إعادة تعيين الجلسة
            user_sessions[user_id] = {"state": "awaiting_token"}

async def analyze_repository(repo_path: Path) -> str:
    """تحليل محتويات المستودع وإرجاع النتائج"""
    code_files = []
    supported_extensions = {".py", ".java", ".kt", ".js", ".ts", ".html", ".css"}
    
    # جمع ملفات الكود
    for ext in supported_extensions:
        code_files.extend(repo_path.rglob(f"*{ext}"))
    
    if not code_files:
        return "⚠️ لم يتم العثور على ملفات كود مدعومة في المستودع."
    
    # تحليل كل ملف
    analysis_results = []
    for file in code_files:
        try:
            with open(file, "r", encoding="utf-8") as f:
                content = f.read()
                
                # إنشاء موجه للتحليل
                prompt = (
                    f"تحليل ملف {file.name}:\n"
                    f"```{file.suffix[1:]}\n{content[:2000]}\n```\n"
                    "قم بتحليل هذا الكود واقترح تحسينات أو أصلح الأخطاء إن وجدت."
                )
                
                # استدعاء النموذج
                response = llm.create_chat_completion(
                    messages=[{"role": "user", "content": prompt}],
                    temperature=0.7,
                    max_tokens=1000
                )
                
                analysis = response["choices"][0]["message"]["content"]
                analysis_results.append(f"📄 {file.name}:\n{analysis}\n")
                
        except Exception as e:
            logger.error(f"Error analyzing {file}: {str(e)}")
            analysis_results.append(f"❌ خطأ في تحليل {file.name}: {str(e)}\n")
    
    # دمج النتائج مع مراعاة حد طول الرسالة في تليجرام
    full_analysis = "\n".join(analysis_results)
    return full_analysis[:4000]  # حد تليجرام للرسائل الطويلة

def main():
    """تشغيل البوت"""
    application = ApplicationBuilder().token(TELEGRAM_BOT_TOKEN).build()
    
    # تسجيل المعالجين
    application.add_handler(CommandHandler("start", start))
    application.add_handler(MessageHandler(filters.TEXT & ~filters.COMMAND, handle_message))
    
    # بدء البوت
    application.run_polling()
    logger.info("Bot is running...")

if __name__ == "__main__":
    main()
