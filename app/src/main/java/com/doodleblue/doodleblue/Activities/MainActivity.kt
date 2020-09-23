package com.doodleblue.doodleblue.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.doodleblue.doodleblue.R
import com.doodleblue.doodleblue.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //toolbar.title="Prices"
        toolbar_title.text="Prices"
        openFragment(PricesFragment.newInstance())
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    private val mOnNavigationItemSelectedListener=BottomNavigationView.OnNavigationItemSelectedListener {menuItem ->
        when (menuItem.itemId){
            R.id.page_prices ->{
                toolbar_title.text="Prices"
                openFragment(PricesFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.page_favouite ->{
                toolbar_title.text="Favourite"
                openFragment(FavouriteFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.page_portfolio ->{
                toolbar_title.text="Portfolio"
                openFragment(PortfolioFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.page_news ->{
                toolbar_title.text="News"
                openFragment(NewsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.page_invest ->{
                toolbar_title.text="Invest"
                openFragment(InvestFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    fun openFragment(fragment: Fragment?) {
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Container_frames, fragment!!)
        //transaction.addToBackStack(null);
        transaction.commit()
    }
}