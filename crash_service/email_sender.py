import ssl
import smtplib


class sender:

    def __init__(self, email, password):
        self.port = 465
        self.server = "smtp.gmail.com"
        self.context = ssl.create_default_context()
        self.email = email
        self.password = password

    def send(self, subject="Message from TakiTaki CrashReport mail service!", message="Check reports!"):

        try:
            # Connect
            with smtplib.SMTP_SSL("smtp.gmail.com", self.port, context=self.context) as server:

                # Login
                server.login(self.email, self.password)

                # Send
                server.sendmail(self.email, self.email, f"Subject: {subject}\n{message}\nTakiTaki")

        except:
            print(f"INFO:     Error while sending report email!")
