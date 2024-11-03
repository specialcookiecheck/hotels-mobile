package org.wit.hotels.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel(var userId: Long = 0,
                      var userFirstName: String = "",
                      var userLastName: String = "",
                      var userEmail: String = "",
                      var userPhone: String = "",
                      var userDateOfBirth: String = "") : Parcelable
