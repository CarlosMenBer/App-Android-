package com.example.apiaplicacion

//Su objetivo es enviar una notificación al usuario cuando ocurre algún evento, como la búsqueda de un Pokémon en la app.


// Importaciones necesarias para gestionar notificaciones en Android
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

// Función para mostrar una notificación en la aplicación
fun showNotification(context: Context, title: String, content: String) {
    val channelId = "pokemon_channel"

    // Verifica si la versión de Android requiere canales de notificación
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Pokémon Notifications"
        val descriptionText = "Notifications for Pokémon searches"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        // Obtiene el servicio de notificación y crea el canal
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Construcción de la notificación
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    // Comprobar si el permiso de notificación está concedido
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    } else {
        // Si no tiene permisos, los solicita al usuario (solo si el contexto es MainActivity)
        if (context is MainActivity) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
    }
}
