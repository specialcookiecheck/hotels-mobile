package org.wit.hotels.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                      var firstName: String = "",
                      var lastName: String = "",
                      var email: String = "",
                      var phone: String = "",
                      var age: Int = 0) : Parcelable
