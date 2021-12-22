package com.example.myapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var btn: Button?=null ;
    private var statusText: TextView ?=null;
    private var stringBuilder:StringBuilder?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn= findViewById<Button>(R.id.btn_async);
        statusText=findViewById<TextView>(R.id.statusText)
        statusText?.movementMethod=ScrollingMovementMethod()
        stringBuilder = StringBuilder("Вывод сообщений на экран\n")
        statusText?.text = "${stringBuilder.toString()}"
        btn?.setOnClickListener(View.OnClickListener {
            var  task:MyAsyncTask = MyAsyncTask()
            task.execute(10)
        })
    }
    inner class MyAsyncTask : AsyncTask<Int, Int, Int>(){
        override fun doInBackground(vararg params: Int?): Int {
            val count:Int?=params[0]
            var index=0
            while (index< count!!){
                publishProgress(index+1)
                Thread.sleep(2000)
                index++
            }
            return count;
        }
        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            if(values[0]!=null) {
                stringBuilder?.append("Сообщение с номером ${values[0]}\n")
                statusText?.text = stringBuilder.toString()
            }
        }
        override fun onPreExecute() {
            super.onPreExecute()
            statusText?.text = "${stringBuilder.toString()}"
        }
        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            statusText?.text = "${stringBuilder.toString()}"
        }
    }
}