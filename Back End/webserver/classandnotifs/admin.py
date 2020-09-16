from django.contrib import admin
from .models import Classroom, Studnet_Classroom, Notification

# Register your models here.

admin.site.register({Classroom, Studnet_Classroom, Notification})
