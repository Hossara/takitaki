import os
from fastapi import FastAPI
from dotenv import load_dotenv
from email_sender import sender
from Mail import Mail

app = FastAPI()
load_dotenv()

# Smtp Config
smtp_email = os.getenv("SMTP_EMAIL")
smtp_password = os.getenv("SMTP_PASS")
mail = sender(smtp_email, smtp_password)

# Start Logs
print(f"INFO:     Mail service started!")
print(f"INFO:     SMTP_EMAIL: {smtp_email}")
print(f"INFO:     SMTP_PASS: {smtp_password}")


@app.post("/mail")
async def root(email: Mail):

    print(f"INFO:     New mail: {email.subject} | {email.to}")
    mail.send(email.subject, email.message, email.to)
