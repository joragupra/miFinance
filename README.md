Personal accounting for Android devices
=======================================

Helps keeping your personal accounting.

The main logic (project balance) is a platform agnostic general ledger module.
It's designed following an event sourced architecture. However, events persistence
(in the balance-persistence project) is implemented only for Android devices, as
it uses the SQLite database which comes with Android OS.
