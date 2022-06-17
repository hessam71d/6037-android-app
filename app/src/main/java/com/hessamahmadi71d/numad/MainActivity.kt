package com.hessamahmadi71d.numad

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import com.hessamahmadi71d.numad.G.Companion.sharedPreferences


class MainActivity : AppCompatActivity() {


    private lateinit var tbtn: MaterialButtonToggleGroup
    private lateinit var et: EditText
    private lateinit var tv: TextView
    private lateinit var ivPaste: ImageView
    private lateinit var ivDelete: ImageView
    private lateinit var ivSettings: ImageView
    private lateinit var settingsModalBottomSheet: SettingsModalBottomSheet

//    private lateinit var modalBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        setListeners()
        convert()

    }


    private fun initialize() {


        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


        et = findViewById(R.id.editText)
        tv = findViewById(R.id.textView)
        ivSettings = findViewById(R.id.ivSettings)
        settingsModalBottomSheet = SettingsModalBottomSheet()




        ivPaste = findViewById(R.id.ivPaste)
        ivDelete = findViewById(R.id.ivDelete)


        tbtn = findViewById(R.id.toggleButton)


        if (sharedPreferences.getBoolean("auto_save", true)) {
            et.setText(sharedPreferences.getString("etInput", getString(R.string.sample_input)))
            et.setSelection(et.text.toString().length)
        }



        if (sharedPreferences.getInt("rdEn2Fa", 0) == 0) {

            tbtn.check(R.id.btnFA2EN)

        } else {
            tbtn.check(R.id.btnEN2FA)

        }
    }

    private fun setListeners() {
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {


                if (sharedPreferences.getBoolean("auto_save", true)) {
                    sharedPreferences.edit().putString("etInput", s.toString()).apply()
                }
                convert()


            }
        })


        ivSettings.setOnClickListener {

            // this if added because double click on show bottom sheet causes crash
            if (!settingsModalBottomSheet.isVisible) {
                settingsModalBottomSheet.show(supportFragmentManager, SettingsModalBottomSheet.TAG)
            }


        }

        settingsModalBottomSheet.setOnSettingsChangedListener(object :
            SettingsModalBottomSheet.SettingsChangedListener {
            override fun onSettingsChange(boolSave: Boolean, boolOnlyNumeric: Boolean) {

                // save change
                sharedPreferences.edit().putBoolean("remove_non_numeric", boolOnlyNumeric).apply()
                convert()

                // numeric change
                sharedPreferences.edit().putBoolean("auto_save", boolSave).apply()

                when (boolSave) {
                    true -> {
                        sharedPreferences.edit().putString("etInput", et.text.toString()).apply()
                    }
                    false -> {
                        sharedPreferences.edit().remove("etInput").apply()
                    }
                }

            }
        })



        tv.setOnClickListener {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", tv.text))

            Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.snackBar_copied),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }


        ivPaste.setOnClickListener {
            val pasteData: ClipData? = clipboardManager.primaryClip
            val item = pasteData?.getItemAt(0)
            val paste = item?.text.toString()

            et.setText(paste)
            et.setSelection(et.text.toString().length)
        }

        ivDelete.setOnClickListener {
            et.setText("")
        }


        tbtn.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (checkedId == R.id.btnEN2FA) {
                sharedPreferences.edit().putInt("rdEn2Fa", 1).apply()
                convert()
            } else {
                sharedPreferences.edit().putInt("rdEn2Fa", 0).apply()
                convert()
            }

        }


    }

    private fun convert() {


        var str = et.text.toString()

        if (str.isEmpty()) {
            tv.visibility = View.INVISIBLE
            return
        }


        if (tbtn.checkedButtonId == R.id.btnFA2EN) {
            str = str.replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9")

                    // arabic
                .replace("٠", "0")
                .replace("١", "1")
                .replace("٢", "2")
                .replace("٣", "3")
                .replace("٤", "4")
                .replace("٥", "5")
                .replace("٦", "6")
                .replace("٧", "7")
                .replace("٨", "8")
                .replace("٩", "9")


            if (sharedPreferences.getBoolean("remove_non_numeric", true)) {
                str = str.replace("[^0-9]".toRegex(), "")
            }

        } else {
            str = str
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹")

                // arabic
                .replace("٠", "۰")
                .replace("١", "۱")
                .replace("٢", "۲")
                .replace("٣", "۳")
                .replace("٤", "۴")
                .replace("٥", "۵")
                .replace("٦", "۶")
                .replace("٧", "۷")
                .replace("٨", "۸")
                .replace("٩", "۹")

            if (sharedPreferences.getBoolean("remove_non_numeric", true)) {
                str = str.replace("[^۰۱۲۳۴۵۶۷۸۹]".toRegex(), "")
            }
        }


        if (str.isEmpty()) {
            tv.visibility = View.INVISIBLE
            return
        }

        tv.visibility = View.VISIBLE
        tv.text = str


    }

    override fun onBackPressed() {

        val appVisitNumber = sharedPreferences.getInt("app_visit_number", 0)
        sharedPreferences.edit().putInt("app_visit_number", appVisitNumber + 1).apply()


        // ask to RATE APP
        if (appVisitNumber == 10) {

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.rate_please_title))
                .setCancelable(false)
                .setMessage(getString(R.string.do_you_like_this_app))
                .setPositiveButton(getString(R.string.rate_yes)) { _, _ ->
                    AlertDialog.Builder(this@MainActivity)
                        .setCancelable(false)
                        .setMessage(getString(R.string.rate_body))
                        .setPositiveButton(getString(R.string.rate_yes)) { _, _ ->
                            G.goToAppPage(this@MainActivity)
                        }
                        .setNegativeButton(getString(R.string.rate_no), null)
                        .show()
                }
                .setNegativeButton(getString(R.string.rate_no)) { _, _ ->
                    AlertDialog.Builder(this@MainActivity)
                        .setCancelable(false)
                        .setMessage(getString(R.string.do_u_want_to_tell_us_why))
                        .setPositiveButton(
                            getString(R.string.rate_yes)
                        ) { _, _ -> G.mail(this@MainActivity) }
                        .setNegativeButton(getString(R.string.rate_no), null)
                        .show()
                }
                .show()
        } else {
            super.onBackPressed()
        }

    }


}