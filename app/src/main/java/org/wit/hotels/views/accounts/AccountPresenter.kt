package org.wit.hotels.views.accounts;

import android.app.Activity.RESULT_OK
import org.wit.hotels.main.MainApp
import org.wit.hotels.models.UserModel
import timber.log.Timber.i

class AccountPresenter(private val view: AccountView) {

    var user = UserModel()
    var app: MainApp = view.application as MainApp
    var edit = false

    init {
        println("AccountPresenter init started")
        if (view.intent.hasExtra("user_edit")) {
            println("user_edit confirmed")
            edit = true
            //Requires API 33+
            //users = view.intent.getParcelableExtra("users_edit",UserModel::class.java)!!
            user = view.intent.extras?.getParcelable("user_edit")!!
            view.showUser(user)
        }
    }

    fun doAddOrSave(firstName: String, lastName: String, email: String, phone: String, dateOfBirth: String) {
        println("AccountPresenter doAddOrSave started")
        user.firstName = firstName
        user.lastName = lastName
        user.email = email
        user.phone = phone
        user.dateOfBirth = dateOfBirth

        i(user.firstName)
        if (edit) {
            i("existing user, attempting database update")
            app.users.update(user)
        } else {
            i("new user, attempting database create")
            app.users.create(user)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        println("AccountPresenter doCancel started")
        view.finish()
    }

    fun doDelete() {
        println("AccountPresenter doDelete started")
        view.setResult(99)
        app.users.delete(user)
        view.finish()
    }
}
