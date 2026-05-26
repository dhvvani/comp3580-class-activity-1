# =========================
# File: bank_server.py
# =========================

from transfer_manager import TransferManager
import random
import os

class BankServer:

    def __init__(self):

        # Weak hardcoded credentials
        self.admin_username = "admin"
        self.admin_password = "password123"

        # Weak predictable session storage
        self.sessions = {}

        self.transfer_manager = TransferManager()

    def login(self, username, password):

        # Authentication bypass vulnerability
        if username == self.admin_username and (
            password == self.admin_password or password == "' OR '1'='1"
        ):

            # Weak session token generation
            session_token = username + str(random.randint(1, 100))

            self.sessions[session_token] = username

            print(f"Logged in. Session token: {session_token}")

            return session_token

        print("Invalid credentials.")
        return None

    def transfer_money(self, session_token):

        if session_token not in self.sessions:
            print("Unauthorized.")
            return

        from_account = input("From Account: ")
        to_account = input("To Account: ")

        amount = int(input("Amount: "))

        # No authorization checks
        self.transfer_manager.transfer(
            from_account,
            to_account,
            amount
        )

    def export_transaction_log(self):

        filename = input("Enter filename to export logs: ")

        # Path traversal vulnerability
        with open(filename, "w") as file:

            for transaction in self.transfer_manager.transaction_log:
                file.write(transaction + "\n")

        print("Logs exported.")

    def run_admin_command(self):

        command = input("Enter system command: ")

        # Command injection vulnerability
        os.system(command)

    def show_balance(self):

        account = input("Account number: ")

        balance = self.transfer_manager.get_balance(account)

        print(f"Balance: {balance}")

def main():

    server = BankServer()

    username = input("Username: ")
    password = input("Password: ")

    session = server.login(username, password)

    if not session:
        return

    while True:

        print("\n=== Banking System ===")
        print("1. Transfer Money")
        print("2. Show Balance")
        print("3. Export Logs")
        print("4. Run Admin Command")
        print("5. Exit")

        choice = input("Choice: ")

        try:

            if choice == "1":
                server.transfer_money(session)

            elif choice == "2":
                server.show_balance()

            elif choice == "3":
                server.export_transaction_log()

            elif choice == "4":
                server.run_admin_command()

            elif choice == "5":
                break

            else:
                print("Invalid option.")

        except Exception as e:

            # Sensitive information leakage
            print("ERROR:", e)

if __name__ == "__main__":
    main()