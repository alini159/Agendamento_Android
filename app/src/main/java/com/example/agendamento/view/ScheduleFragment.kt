package com.example.agendamento.view

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.agendamento.databinding.ScheduleFragmentBinding
import com.example.agendamento.model.UserEvent
import com.example.agendamento.viewModel.LoginViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ScheduleFragment : Fragment() {

    private lateinit var binding: ScheduleFragmentBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private val database: FirebaseFirestore = Firebase.firestore
    private lateinit var event: UserEvent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScheduleFragmentBinding.inflate(inflater, container, false)
        setupSchedule()
        return binding.root
    }

    private fun setupCalendar(date: String?) {
        val parts = date?.split("-")
        val day = parts?.get(2)?.let { Integer.parseInt(it) }
        val mounth = parts?.get(1)?.let { Integer.parseInt(it) - 1 }
        val year = parts?.get(0)?.let { Integer.parseInt(it) }
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year!!)
        calendar.set(Calendar.MONTH, mounth!!)
        calendar.set(Calendar.DAY_OF_MONTH, day!!)

        val milliTime = calendar.timeInMillis

        binding.calendarView.setDate(milliTime, true, true)
    }

    private fun setupSchedule() {
        val doc = database.collection("Appointments")
            .document(viewModel.getCurretUser().nextConsult.toString())
        doc.get().addOnSuccessListener { task ->
            event = UserEvent(
                patient = task.data?.getValue("patient").toString(),
                start = task.data?.getValue("start").toString(),
                details = task.data?.getValue("details").toString(),
                title = task.data?.getValue("name").toString(),
            )
            val dateList = event.start?.split(" ")
            binding.title.text = event.title
            binding.name.text = event.patient
            binding.textDescription.text = event.details
            binding.data.text = formatterDateString(dateList?.get(0))
            binding.hours.text = dateList?.get(1)

            setupCalendar(dateList?.get(0))
            createNotification(dateList?.get(0), dateList?.get(1))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            }
        }
    }

    private fun formatterDateString(date: String?): String {
        val stringList = date?.split("-")
        var day = stringList?.get(2)
        var mounth = stringList?.get(1)
        var year = stringList?.get(0)
        return "${day}/${mounth}/${year}"
    }

    private fun createNotification(date: String?, time: String?) {
        val intent = Intent(requireContext().applicationContext, Notification::class.java)
        val title = "Não se esqueça !!!"
        val message = "Temos consulta marcada em 1 Hora 💛💛💛🥰"
        intent.putExtra(titleEX, title)
        intent.putExtra(messageEX, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireActivity().applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(date, time)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat =
            android.text.format.DateFormat.getLongDateFormat(requireActivity().applicationContext)
        val timeFormat =
            android.text.format.DateFormat.getTimeFormat(requireActivity().applicationContext)

        AlertDialog.Builder(requireContext()).setTitle("Atenção !!!").setMessage(
            "Terapia é um Ato de Coragem!" +
                    "\nAs: " + dateFormat.format(date) + " " + timeFormat.format(date)
        ).setPositiveButton("Ok") { _, _ -> }.show()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Não se esqueça !!!"
        val desc = "Você tem uma consulta marcada daqui a 1 Hora 💛💛💛🥰"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun getTime(date: String?, hour: String?): Long {
        val parts = date?.split("-")
        val hourMinute = hour?.split(":")
        val minute = hourMinute?.get(1)?.let { Integer.parseInt(it) - 2} // remover o -2
        val hour = hourMinute?.get(0)?.let { Integer.parseInt(it) } //adicionar -1 para chamar 1 hr antes
        val day = parts?.get(2)?.let { Integer.parseInt(it) }
        val mounth = parts?.get(1)?.let { Integer.parseInt(it) - 1 }
        val year = parts?.get(0)?.let { Integer.parseInt(it) }
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year!!)
        calendar.set(Calendar.MONTH, mounth!!)
        calendar.set(Calendar.DAY_OF_MONTH, day!!)
        calendar.set(Calendar.HOUR_OF_DAY, hour!!)
        calendar.set(Calendar.MINUTE, minute!!)

        return calendar.timeInMillis

    }

}