from django.contrib import admin
from django.urls import path, include
from .views import *


urlpatterns = [

    path('get_classrooms/<str:username>/<str:type>/', get_classrooms, name="get-classrooms"),
    path('get_notifications_by_user/<str:username>/<str:type>/', get_notifications_by_user, name="get-notifications-by-user"),
    path('get_notifications_by_class/<str:name>/', get_notifications_by_class),
    path('add_student_to_class/<str:_id>/<str:class_name>/', add_student_to_class, name="add-student-to-class"),
    path('create_class/<str:teacher_name>/<str:class_name>/', create_class, name="create-class"),
    path('create_notification/<str:class_name>/<str:title>/<str:subject>/<str:content>/<str:dead_year>/<str:dead_month>/<str:dead_day>/<str:dead_hour>/<str:dead_min>/', create_notification, name="create-notification"),


]
