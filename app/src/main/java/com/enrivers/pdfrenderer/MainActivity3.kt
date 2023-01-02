package com.enrivers.pdfrenderer


import android.animation.TimeAnimator
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextPaint
import android.util.TypedValue
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity3 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        supportActionBar?.hide()

        createNotificationChannel()



        val progress =findViewById<ProgressBar>(R.id.progress)
        val sampleText = findViewById<TextView>(R.id.sampleText)
        val paint: TextPaint = sampleText.paint
        val width: Float = paint.measureText(sampleText.text.toString())
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, resources.displayMetrics)
        progress.indeterminateDrawable = scrollingGradient(px)




        //        val textShader: Shader = LinearGradient(
        //            0f, 0f, width, sampleText.textSize, intArrayOf(
        //                Color.parseColor("#8913FC"),
        //                Color.parseColor("#00BFFC"),
        //                Color.parseColor("#00EFFC")
        //            ), null, Shader.TileMode.CLAMP
        //        )
        //        sampleText.paint.shader = textShader
        //        val textShader: Shader = LinearGradient(0f, 0f, width, sampleText.textSize, intArrayOf(Color.parseColor("#8913FC"), Color.parseColor("#00BFFC"), Color.parseColor("#00EFFC")), null, Shader.TileMode.CLAMP)
        //        sampleText.paint.shader = textShader



        val checkStatusNow = findViewById<CardView>(R.id.checkStatusNow)
        checkStatusNow.setOnClickListener {
            val popUp = MaterialAlertDialogBuilder(this)
            val view = layoutInflater.inflate(R.layout.delete_pop_up_dialog, null)
            popUp.setView(view)
            val alertDialog = popUp.create()
            val noBtn: MaterialButton = view.findViewById(R.id.no)
            val yesBtn: MaterialButton = view.findViewById(R.id.yes)
            val closeDialog: ImageView = view.findViewById(R.id.closeDialog)
            closeDialog.setOnClickListener {
                alertDialog.dismiss()
            }
            noBtn.setOnClickListener {
                createNotificationChannel()
            }
            yesBtn.setOnClickListener {
                Toast.makeText(
                    this,
                    "Distance ${
                        distance(
                            20.342160,
                            85.806187,
                            20.282750948064034,
                            85.7505078218046
                        )
                    }",
                    Toast.LENGTH_SHORT
                ).show()
            }

            alertDialog.setCancelable(false)
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.show()
        }


    }

    private fun showCircularProgressBar() {

        val popUp = MaterialAlertDialogBuilder(this)
        val view = layoutInflater.inflate(R.layout.custom_progressbar, null)
        popUp.setView(view)
        val alertDialog = popUp.create()


        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()


    }


    private fun navigationMap() {
//        val uri: String = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 20.282750948064034, 85.7505078218046)
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//        startActivity(intent)

        val gmmIntentUri = Uri.parse("google.navigation:q=20.282750948064034, 85.7505078218046")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)


    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val location1 = Location("")
        location1.latitude = lat1
        location1.longitude = lon1
        val location2 = Location("")
        location2.latitude = lat2
        location2.longitude = lon2
        val distance = location1.distanceTo(location2)
        return distance / 1000
    }

//
//    private val scrollingGradient =object :Drawable(),Animatable,TimeAnimator.TimeListener{
//        val pixelPerSecond = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, resources.displayMetrics)
//        private val paint = Paint()
//        private var startPosition: Float = 0.toFloat()
//        private val animator = TimeAnimator()
//        init {
//            animator.setTimeListener(this)
//        }
//
//        override fun onBoundsChange(bounds: Rect) {
//            paint.shader = LinearGradient(0f, 0f, bounds.width().toFloat(), 0f,intArrayOf(Color.RED, Color.WHITE, Color.BLUE) ,null, Shader.TileMode.MIRROR)
//        }
//
//
//
//        override fun draw(canvas: Canvas) {
//            canvas.clipRect(bounds)
//            canvas.translate(startPosition, 0f)
//            canvas.drawPaint(paint)
//        }
//
//        override fun setAlpha(alpha: Int) {}
//
//        override fun setColorFilter(colorFilter: ColorFilter?) {}
//
//
//        override fun getOpacity(): Int  = PixelFormat.TRANSLUCENT
//
//        override fun start() {
//            animator.start()
//        }
//
//        override fun stop() {
//           animator.cancel()
//        }
//
//        override fun isRunning(): Boolean = animator.isRunning
//
//        override fun onTimeUpdate(animation: TimeAnimator?, totalTime: Long, deltaTime: Long) {
//            startPosition = pixelPerSecond * totalTime / 1000
//            invalidateSelf()
//        }
//    }
//


    class scrollingGradient(private val pixelsPerSecond: Float) : Drawable(), Animatable, TimeAnimator.TimeListener {
        private val paint = Paint()
        private var x: Float = 0.toFloat()
        private val animator = TimeAnimator()

        init {
            animator.setTimeListener(this)
        }

        override fun onBoundsChange(bounds: Rect) {
            paint.shader = LinearGradient(0f, 0f, bounds.width().toFloat(), 0f,intArrayOf(Color.RED, Color.WHITE, Color.BLUE) ,null, Shader.TileMode.MIRROR)
        }

        override fun draw(canvas: Canvas) {
            canvas.clipRect(bounds)
            canvas.translate(x, 0f)
            canvas.drawPaint(paint)
        }

        override fun setAlpha(alpha: Int) {}

        override fun setColorFilter(colorFilter: ColorFilter?) {}

        override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

        override fun start() {
            animator.start()
        }

        override fun stop() {
            animator.cancel()
        }

        override fun isRunning(): Boolean = animator.isRunning

        override fun onTimeUpdate(animation: TimeAnimator, totalTime: Long, deltaTime: Long) {
            x = pixelsPerSecond * totalTime / 1000
            invalidateSelf()
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun createNotificationChannel() {
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "this is description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("120", "Notification Name", importance).apply { description = descriptionText }
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel)
        }

        val fullScreenIntent = Intent(this, MainActivity4::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
            fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = NotificationCompat.Builder(this, "120")
            .setSmallIcon(R.drawable.circle_drawable)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setContentIntent(fullScreenPendingIntent)

        val notificationBuilder = builder.build()
        notificationBuilder.flags = notificationBuilder.flags or (Notification.FLAG_NO_CLEAR or Notification.FLAG_ONGOING_EVENT)
        notificationManager.notify(230,notificationBuilder)

    }

}