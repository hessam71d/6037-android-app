package com.hessamahmadi71d.numad

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate

class G : Application() {
    override fun onCreate() {
        super.onCreate()


        sharedPreferences =
            getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)


        val nightMode: Int = sharedPreferences.getInt("night_mode", 2)
        if (nightMode != 2) {
            if (nightMode == 0) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


    companion object {

        const val Base_market_address = "https://play.google.com/store/apps/details?id="
        const val other_apps_url = "https://play.google.com/store/apps/developer?id=Hessam+Ahmadi"

        lateinit var sharedPreferences: SharedPreferences


        fun goToAppPage(context: Context) {

            //google play
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data =
                    Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                intent.setPackage("com.android.vending")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }


            // non gp
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(Base_market_address + BuildConfig.APPLICATION_ID));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        }

        /* public static void privacy(Context context) {
        G.openUrl(context, "https://vazinapps.blogspot.com/2021/06/fortia-app.html");
    }*/

        fun shareApp(context: Context) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = """
                 ${context.getString(R.string.app_name)}
                 
                 $Base_market_address${BuildConfig.APPLICATION_ID}
                 """.trimIndent()
            //        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "اشتراک\u200Cگذاری");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            context.startActivity(Intent.createChooser(sharingIntent, ""))
        }

        fun otherApps(context: Context) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(other_apps_url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun mail(context: Context) {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + Uri.encode("hessamahmadi71d@gmail.com"))
            )
            emailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME
            )
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            context.startActivity(Intent.createChooser(emailIntent, ""))
        }

        fun openUrl(context: Context, url: String?) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }
}
