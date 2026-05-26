# =========================
# File: transfer_manager.py
# =========================

import threading
import pickle

class TransferManager:

    def __init__(self):

        # In-memory account balances
        self.accounts = {
            "1001": 5000,
            "1002": 9000,
            "1003": 15000
        }

        self.transaction_log = []

    def transfer(self, from_account, to_account, amount):

        # Race condition vulnerability
        # No locking/synchronization used

        if from_account not in self.accounts:
            print("Source account does not exist.")
            return

        if to_account not in self.accounts:
            print("Destination account does not exist.")
            return

        # Integer manipulation vulnerability
        if amount < 0:
            print("Negative transfers allowed.")
        
        # No sufficient funds check
        self.accounts[from_account] -= amount
        self.accounts[to_account] += amount

        log_entry = (
            f"TRANSFER | FROM={from_account} "
            f"TO={to_account} "
            f"AMOUNT={amount}"
        )

        # Sensitive financial logging
        self.transaction_log.append(log_entry)

        print("Transfer complete.")

    def get_balance(self, account):

        # Broken access control
        return self.accounts.get(account, "Account not found.")

    def save_backup(self, filename):

        with open(filename, "wb") as file:

            # Unsafe serialization
            pickle.dump(self.accounts, file)

    def load_backup(self, filename):

        with open(filename, "rb") as file:

            # Unsafe deserialization vulnerability
            self.accounts = pickle.load(file)

        print("Backup restored.")

    def simulate_concurrent_transfers(self):

        # Denial of service risk through unbounded threads

        while True:

            thread = threading.Thread(
                target=self.transfer,
                args=("1001", "1002", 1)
            )

            thread.start()