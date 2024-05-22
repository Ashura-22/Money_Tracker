package com.project.moneytracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.firebase.auth.FirebaseAuth
import com.project.moneytracker.databinding.ActivityDashboardBinding


class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        binding= ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        //Adding name on UI
        if(firebaseAuth.currentUser!=null)
        {
            firebaseAuth.currentUser.let {
                if (it != null) {
                    val ud = FirebaseAuth.getInstance().uid
                    binding.tvWelcome.text=ud
                }
            }
        }

        //piechart function call here
        piechart()

        //Logout button Code here
        /*binding.logout.setOnClickListener()
        {

            firebaseAuth.signOut()
            //this will redirect to Login page

            val intent= Intent(this,Login_Page::class.java)
            startActivity(intent)

        }*/
    }
    private fun piechart() {
        val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)
        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Needs", 45))
        data.add(ValueDataEntry("Wants", 30))
        data.add(ValueDataEntry("Investments", 25))

        pie.data(data)
        pie.title("Spending Analysis")
        pie.labels().position("outside")

        pie.legend().title().enabled(true)
        pie.legend().title()
            .text("Categories")
            .padding(0.0, 0.0, 10.0, 0.0)

        pie.legend()
            .position("bottom")
            .itemsLayout("horizontal")
            .align("center")

        anyChartView.setChart(pie)
    }

}