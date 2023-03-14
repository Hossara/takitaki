import ssl
import smtplib
from datetime import date


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

                # Send
                server.sendmail(self.email, to, f"Subject: {subject}\n{message}\n© {date.year} TakiTaki")

        except:
            print(f"INFO:     Error while sending report email!")
