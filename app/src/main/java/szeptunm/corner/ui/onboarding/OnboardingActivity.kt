package szeptunm.corner.ui.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import szeptunm.corner.R
import szeptunm.corner.R.layout
import szeptunm.corner.commons.Constants
import szeptunm.corner.databinding.ActivityOnboardingBinding
import szeptunm.corner.domain.splashScreen.IsFirstInitialized
import szeptunm.corner.ui.BaseActivity
import szeptunm.corner.ui.splashScreen.SplashScreenActivity
import javax.inject.Inject

@SuppressLint("Registered")
class OnboardingActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_onboarding

    private lateinit var binding: ActivityOnboardingBinding

    @Inject
    lateinit var isFirstInitialized: IsFirstInitialized

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_onboarding)
        if (!isFirstInitialized.isInitialized) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
            binding.btnChooseClub.setOnClickListener {
                val popUp = PopupMenu(this, it, 0)
                popUp.menuInflater.inflate(R.menu.popup_menu, popUp.menu)
                popUp.setOnMenuItemClickListener { menuItem ->
                    val intent = Intent(this, SplashScreenActivity::class.java)
                    val bundle = Bundle().apply {
                        putString(Constants.KEY_CLUB_FAVOURITE, menuItem.title.toString())
                    }
                    intent.putExtras(bundle)
                    startActivity(intent)
                    true
                }
                popUp.show()
            }
        } else {
            startActivity(Intent(this, SplashScreenActivity::class.java))
        }
    }
}