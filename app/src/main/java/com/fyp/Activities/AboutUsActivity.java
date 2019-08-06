package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.fyp.R;

public class AboutUsActivity extends AppCompatActivity {

    WebView mWebview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mWebview = findViewById(R.id.web);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setDisplayZoomControls(false);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        StringBuilder data = new StringBuilder();
        data.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Home Driven App</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400,700,900\" rel=\"stylesheet\">\n" +
                "\n" +
                "    <!-- BASE CSS -->\n" +
                "    <link href=\"animate.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"style.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"responsive.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"elegant_font/elegant_font.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"pop_up.css\" rel=\"stylesheet\">\n" +
                "\n" +
                "    <!-- YOUR CUSTOM CSS -->\n" +
                "    <link href=\"custom.css\" rel=\"stylesheet\">\n" +
                "\n" +
                "    <!-- Modernizr -->\n" +
                "    <script src=\"modernizr.js\"></script>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>");
        data.append("\n" +
                "<div id=\"preloader\">\n" +
                "    <div class=\"sk-spinner sk-spinner-wave\" id=\"status\">\n" +
                "        <div class=\"sk-rect1\"></div>\n" +
                "        <div class=\"sk-rect2\"></div>\n" +
                "        <div class=\"sk-rect3\"></div>\n" +
                "        <div class=\"sk-rect4\"></div>\n" +
                "        <div class=\"sk-rect5\"></div>\n" +
                "    </div>\n" +
                "</div><!-- End Preload -->\n" +
                "\n" +
                "\n" +
                "<!-- SubHeader =============================================== -->\n" +
                "<section class=\"parallax-window\" id=\"short\" data-parallax=\"scroll\" data-image-src=\"picss.jpg\"\n" +
                "         data-natural-width=\"1400\" data-natural-height=\"350\">\n" +
                "    <div id=\"subheader\">\n" +
                "        <div id=\"sub_content\">\n" +
                "            <h1>About us</h1>\n" +
                "            <p><q>Stop dreaming start doing</q></p>\n" +
                "            <p></p>\n" +
                "        </div><!-- End sub_content -->\n" +
                "    </div><!-- End subheader -->\n" +
                "</section><!-- End section -->\n" +
                "<!-- End SubHeader ============================================ -->\n" +
                "\n" +
                "<!-- Content ================================================== -->\n" +
                "<div class=\"container margin_60_35\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-4\">\n" +
                "            <h3 class=\"nomargin_top\">Some words about us</h3>\n" +
                "            <p>\n" +
                "                Our basic Purpose is to target those women who not able to work due to social\n" +
                "                restrictions. Our APP is basically provide them way to earn for their selves.\n" +
                "            </p>\n" +
                "            <p>\n" +
                "                It is a real world problem that our society hardly accepts those women\n" +
                "                ( Aged , Divorced, Disabled) who step out from their homes for respectful earning .\n" +
                "                We provide them a motivational platform to encourages them to earn for their selves\n" +
                "                without stepping out.\n" +
                "\n" +
                "            </p>\n" +
                "            <h4>Mission</h4>\n" +
                "            <p>\n" +
                "                Our Mission is to develop people, especially unemployed people, socially,\n" +
                "                economically, and emotionally. ... To succeed and excel to provide work at home\n" +
                "                which will help unemployed work seekers to find and perform work at home.\n" +
                "            </p>\n" +
                "\n" +
                "        </div>\n" +
                "        <div class=\"col-md-7 col-md-offset-1 text-right hidden-sm hidden-xs\">\n" +
                "            <img src=\"picture1.png\" style=\"height:100%;width:100%\" alt=\"\" class=\"img-responsive\">\n" +
                "        </div>\n" +
                "    </div><!-- End row -->\n" +
                "    <hr class=\"more_margin\">\n" +
                "    <div class=\"main_title\">\n" +
                "        <h2 class=\"nomargin_top\"> Quality feautures</h2>\n" +
                "        <!-- <p>\n" +
                "             Cum doctus civibus efficiantur in imperdiet deterruisset.\n" +
                "         </p>-->\n" +
                "    </div>\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-6 wow fadeIn\" data-wow-delay=\"0.1s\">\n" +
                "            <div class=\"feature\">\n" +
                "                <i class=\"icon_building\"></i>\n" +
                "                <h3><span>+ 1000</span> Employees</h3>\n" +
                "                <p>\n" +
                "                    Congratulations for being a part of our team. Our whole team welcomes a new\n" +
                "                    employee like you. We are looking forward for the organization success with you.\n" +
                "                    Welcome aboard.\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-6 wow fadeIn\" data-wow-delay=\"0.2s\">\n" +
                "            <div class=\"feature\">\n" +
                "                <i class=\"icon_documents_alt\"></i>\n" +
                "                <h3><span>Different</span> services Menu</h3>\n" +
                "                <p>\n" +
                "                    We provide different services at one platform .you choose servicee according to\n" +
                "                    your skill and serve the people.\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div><!-- End row -->\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-6 wow fadeIn\" data-wow-delay=\"0.3s\">\n" +
                "            <div class=\"feature\">\n" +
                "                <i class=\"icon_bag_alt\"></i>\n" +
                "                <h3><span>Delivery</span> or Takeaway</h3>\n" +
                "                <p>\n" +
                "                    You call and order. They tell you at what time in which your order will be\n" +
                "                    ready. You go and pick up your order at that time and take it home.\n" +
                "\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-6 wow fadeIn\" data-wow-delay=\"0.4s\">\n" +
                "            <div class=\"feature\">\n" +
                "                <i class=\"icon_mobile\"></i>\n" +
                "                <h3><span>Mobile</span> support</h3>\n" +
                "                <p>\n" +
                "                    The mobile web, also known as mobile internet, refers to browser-based Internet\n" +
                "                    services accessed from handheld mobile devices, such as smartphones or feature\n" +
                "                    phones, through a mobile or other wireless network.\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div><!-- End row -->\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-6 wow fadeIn\" data-wow-delay=\"0.5s\">\n" +
                "            <div class=\"feature\">\n" +
                "                <i class=\"icon_wallet\"></i>\n" +
                "                <h3><span>Cash</span> payment</h3>\n" +
                "                <p>\n" +
                "                    Money paid for goods or services at the time of purchase or delivery.\n" +
                "\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-6 wow fadeIn\" data-wow-delay=\"0.6s\">\n" +
                "            <div class=\"feature\">\n" +
                "                <i class=\"icon_creditcard\"></i>\n" +
                "                <h3><span>Secure cash</span> payment</h3>\n" +
                "                <p>\n" +
                "                    Money paid for goods or services at the time of purchase or delivery in the\n" +
                "                    secure way.\n" +
                "\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div><!-- End row -->\n" +
                "</div><!-- End container -->\n" +
                "\n" +
                "<div class=\"container-fluid\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-6 nopadding features-intro\">\n" +
                "            <div class=\"features-bg \">\n" +
                "                <div class=\"features-img\">\n" +
                "                    <img class=\"img-responsive\" src=\"img.jpg\" style=\"height:100%;width:100%\">\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-6 nopadding\">\n" +
                "            <div class=\"features-content\">\n" +
                "                <p>\n" +
                "                <h4><q><b><i>We are differently availabale</q></b></i></h4>\n" +
                "                </p>\n" +
                "                <p>\n" +
                "                    <q><i><b>It doesn’t matter who you are, where you come from. The ability to\n" +
                "                        triumph begins with you. Always.</i></b></q>\n" +
                "                </p>\n" +
                "                <p>\n" +
                "                    <q><i><b>In the middle of difficulty lies opportunity.</i></b></q>\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div><!-- End container-fluid  -->\n" +
                "<!-- End Content =============================================== -->\n" +
                "\n" +
                "\n" +
                "<div class=\"layer\"></div><!-- Mobile menu overlay mask -->\n" +
                "\n" +
                "\n" +
                "<!-- COMMON SCRIPTS -->\n" +
                "<script src=\"jquery-2.2.4.min.js\"></script>\n" +
                "<script src=\"functions.js\"></script>\n" +
                "<script src=\"validate.js\"></script>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
        mWebview.loadDataWithBaseURL("file:///android_asset/", data.toString(), "text/html", "utf-8", null);
    }
}