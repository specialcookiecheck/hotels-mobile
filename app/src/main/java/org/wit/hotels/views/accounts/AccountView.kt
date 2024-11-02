package org.wit.hotels.views.accounts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.hotels.R
import org.wit.hotels.databinding.ActivityAccountBinding
import org.wit.hotels.models.UserModel
import timber.log.Timber.i

class AccountView : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var presenter: AccountPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        println("AccountView onCreate started")
        super.onCreate(savedInstanceState)

        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = AccountPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        println("AccountView onCreateOptionsMenu started")
        menuInflater.inflate(R.menu.menu_account, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.user_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        i("AccountView onOptionsItemSelected started")
        when (item.itemId) {
            R.id.user_save -> {
                if (binding.accountEditLastName.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_user_lastName, Snackbar.LENGTH_LONG)
                        .show()
                } else if (binding.accountEditFirstName.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_user_firstName, Snackbar.LENGTH_LONG)
                        .show()
                } else if (binding.accountEditEmail.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_user_email, Snackbar.LENGTH_LONG)
                        .show()
                } else if (binding.accountEditPhone.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_user_phone, Snackbar.LENGTH_LONG)
                        .show()
                } else if (binding.accountEditDateOfBirth.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_user_dateOfBirth, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    presenter.doAddOrSave(
                        binding.accountEditFirstName.text.toString(),
                        binding.accountEditLastName.text.toString(),
                        binding.accountEditEmail.text.toString(),
                        binding.accountEditPhone.text.toString(),
                        binding.accountEditDateOfBirth.text.toString(),
                    )
                }
            }
            R.id.user_delete -> {
                presenter.doDelete()
            }
            R.id.user_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    

    fun showUser(user: UserModel) {
        i("AccountView showUser started")
        i(user.userFirstName)

        binding.accountEditFirstName.setText(user.userFirstName)
        binding.accountEditLastName.setText(user.userLastName)
        binding.accountEditEmail.setText(user.userEmail)
        binding.accountEditPhone.setText(user.userPhone)
        binding.accountEditDateOfBirth.setText(user.userDateOfBirth)
    }
}