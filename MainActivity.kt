package com.example.asynctaskweakreference

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTask.setOnClickListener {

            AsyncTaskExample(this).execute(10)

        }

    }

    companion object{

    class AsyncTaskExample internal constructor(context: MainActivity) : AsyncTask<Int, Int, String>(){

        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        override fun onPreExecute() {
            super.onPreExecute()
          var mainActivity=  activityReference.get()

            mainActivity!!.progress_bar.visibility=View.VISIBLE

        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            activityReference.get()!!.progress_bar.progress= values[0]!!
        }

        override fun doInBackground(vararg params: Int?): String {
            for (i in 0 until params[0]!!) {

                publishProgress(i * 100 / params[0]!!)
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
            return "Task Finished"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            activityReference.get()!!.progress_bar.progress=0?:return
            activityReference.get()!!.progress_bar.visibility=View.INVISIBLE?:return
        }

    }
    }
}
