import os
import pymongo
from bson import json_util
import json
import datetime
from fastapi import FastAPI
from dotenv import load_dotenv
from email_sender import sender

app = FastAPI()
load_dotenv()

# Database Config
db_url = os.getenv("DB_URL")
db_client = pymongo.MongoClient(db_url)
db = db_client["takitaki_crash"]
reports = db["reports"]

# Smtp Config
smtp_email = os.getenv("SMTP_EMAIL")
smtp_password = os.getenv("SMTP_PASS")
mail = sender(smtp_email, smtp_password)

# Start Logs
print(f"INFO:     Crash report service started!")
print(f"INFO:     DB_URL: {db_url}")
print(f"INFO:     DB_NAME: {db.name}")
print(f"INFO:     DB_COLLECTION: {reports.name}")
print(f"INFO:     SMTP_EMAIL: {smtp_email}")
print(f"INFO:     SMTP_PASS: {smtp_password}")


@app.post("/report")
async def root(err: str = "", msg: str = "", err_type: str = "WARNING"):
    # Report Object
    report = {
        "error": err,
        "date": datetime.datetime.now(),
        "message": msg,
        "type": err_type
    }

    # Log Report
    print(f"INFO:     New report: {report}")

    try:
        # Insert Report To Database And Collect Report ID
        insert = reports.insert_one(report)
        report_id = json.loads(json_util.dumps(insert.inserted_id))["$oid"]

        # Send Report Alert Email
        mail.send(f"{err} Error!", f"{msg}\nError type: {err_type}")

        # Log Report And Finish
        print(f"INFO:     Report saved with id: {report_id}")
        return {"status": "success", "id": report_id}

    except Exception as e:
        # Send Error Alert Email
        mail.send(message="Error while saving report")

        # Log Report And Finish
        print(f"INFO:     Error while saving report")
        print(f"INFO:     {e}")
        return {"status": "error", "msg": "error while saving report"}
