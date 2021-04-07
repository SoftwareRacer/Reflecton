import sys
from pyicloud import PyiCloudService
from datetime import datetime

api = PyiCloudService(str(sys.argv[1]), str(sys.argv[2]))
startDate = str(sys.argv[4])
startDateArray = startDate.split("-")

from_dt = datetime(int(startDateArray[0]), int(startDateArray[1]), int(startDateArray[2]))
if startDateArray[1] < 12:
    to_dt = datetime(int(startDateArray[0]), int(startDateArray[1])+1, int(startDateArray[2]))
else:
    to_dt = datetime(int(startDateArray[0]), 12, 31)

events = api.calendar.events(from_dt, to_dt)
print(events)
