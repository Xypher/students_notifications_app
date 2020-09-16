from django.contrib import admin
from django.urls import path, include
from .views import *

urlpatterns = [

    path('login/<str:username>/<str:password>/', login, name="login"),
    path('signup/<str:username>/<str:password>/<str:fullname>/<str:_id>/<str:email>/<str:type>/', signup, name="signup"),
]
#username, password, fullname, _id, email, type