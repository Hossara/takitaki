from pydantic import BaseModel


class Mail(BaseModel):
    subject: str
    message: str
    to: str
