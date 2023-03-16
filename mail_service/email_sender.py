import ssl
import smtplib
from datetime import date
from email.message import EmailMessage


class sender:

    def __init__(self, email, password):
        self.port = 465
        self.server = "smtp.gmail.com"
        self.context = ssl.create_default_context()
        self.email = email
        self.password = password

    def send(self, subject="TakiTaki!", message="new empty email from server", to=""):

        try:
            # Connect
            with smtplib.SMTP_SSL("smtp.gmail.com", self.port, context=self.context) as server:

                # Login
                server.login(self.email, self.password)

                # Message
                em = EmailMessage()
                em.set_content(f"{message}\n\n© {date.today().year} TakiTaki")

                # Message Information
                em["To"] = to
                em["From"] = self.email
                em["Subject"] = subject

                # Send
                server.send_message(em)

        except Exception as e:
            print(e)
            print(f"INFO:     Error while sending email!")
