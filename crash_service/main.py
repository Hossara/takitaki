import os
import pymongo
from bson import json_util
import json
import datetime
from fastapi import FastAPI
from dotenv import load_dotenv

app = FastAPI()
load_dotenv()

db_url = os.getenv("DB_URL")
db_client = pymongo.MongoClient(db_url)
db = db_client["takitaki_crash"]
reports = db["reports"]

print(f"INFO:     Crash report service started!")
print(f"INFO:     DB_URL: {db_url}")
print(f"INFO:     DB_NAME: {db.name}")
print(f"INFO:     DB_COLLECTION: {reports.name}")


@app.post("/report")
async def root(err: str = "", msg: str = "", err_type: str = "WARNING"):
    report = {
        "error": err,
        "date": datetime.datetime.now(),
        "message": msg,
        "type": err_type
    }

    print(f"INFO:     New report: {report}")

    try:
        insert = reports.insert_one(report)
        report_id = json.loads(json_util.dumps(insert.inserted_id))["$oid"]

        print(f"INFO:     Report saved with id: {report_id}")
        return {"status": "success", "id": report_id}

    except:
        print(f"INFO:     Error while saving report")
        return {"status": "error", "msg": "error while saving report"}
