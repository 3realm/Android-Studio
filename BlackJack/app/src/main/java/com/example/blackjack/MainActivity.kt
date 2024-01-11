package com.example.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.SeekBar
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import com.example.blackjack.R
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar.OnSeekBarChangeListener
import java.util.*

class MainActivity : AppCompatActivity() {
    var result: TextView? = null
    var bot: TextView? = null
    var bresNum: TextView? = null
    var resNum: TextView? = null
    var winLose: TextView? = null
    var cash: TextView? = null
    var sbRes: TextView? = null
    var seekBar: SeekBar? = null
    private var button: Button? = null
    private var next: Button? = null
    private var stop: Button? = null
    var pCard_1: ImageView? = null
    var pCard_2: ImageView? = null
    var pCard_3: ImageView? = null
    var pCard_4: ImageView? = null
    var bCard_1: ImageView? = null
    var bCard_2: ImageView? = null
    var bCard_3: ImageView? = null
    var bCard_4: ImageView? = null
    var backCard_4: ImageView? = null
    var resCash = 0
    var check34 = 0
    var cardSound: MediaPlayer? = null
    fun cardToInt(a: String?): Int {
        val b = a!![0]
        val c = a[1]
        var res = 0
        res = if ((b != 'j') and (b != 'q') and (b != 'k') and (b != 'a') and (c != '0')) Character.getNumericValue(b) else {
            10
        }
        return res
    }

    fun bjCheck(a: String?, b: String?): Boolean {
        var res = true
        val c1 = a!![0]
        val c2 = a[1]
        val cc1 = b!![0]
        val cc2 = b[1]
        if ((c1 != 'j') and (c1 != 'q') and (c1 != 'k') and (c1 != 'a') and (c2 != '0') or
                ((cc1 != 'j') and (cc1 != 'q') and (cc1 != 'k') and (cc1 != 'a') and (cc2 != '0'))) res = false
        if ((c2 == '0') and (cc2 == '0')) res = false
        return res
    }

    fun cardToId(a: String?): Int {
        val id = "z$a"
        return resources.getIdentifier(id, "drawable", packageName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.result)
        bot = findViewById(R.id.bot)
        button = findViewById(R.id.button)
        next = findViewById(R.id.next)
        bresNum = findViewById(R.id.bresNum)
        winLose = findViewById(R.id.winOrLose)
        pCard_1 = findViewById(R.id.firstCard)
        pCard_2 = findViewById(R.id.firstCard2)
        pCard_3 = findViewById(R.id.firstCard3)
        pCard_4 = findViewById(R.id.firstCard4)
        bCard_1 = findViewById(R.id.botCard1)
        bCard_2 = findViewById(R.id.botCard2)
        bCard_3 = findViewById(R.id.botCard3)
        bCard_4 = findViewById(R.id.botCard4)
        resNum = findViewById(R.id.resNum)
        backCard_4 = findViewById(R.id.cardbc1)
        next!!.setVisibility(View.INVISIBLE)
        stop = findViewById(R.id.stop)
        stop!!.setVisibility(View.INVISIBLE)
        cash = findViewById(R.id.cash)
        seekBar = findViewById(R.id.seekBar)
        sbRes = findViewById(R.id.seekBarRes)
        button!!.setVisibility(View.INVISIBLE)
        cardSound = MediaPlayer.create(this@MainActivity, R.raw.cardsound)
        val cards = arrayOfNulls<String>(52)
        val anim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.cardanim)
        val animB = AnimationUtils.loadAnimation(this@MainActivity, R.anim.bcardanim)

//        result.setVisibility(View.INVISIBLE);
//        bot.setVisibility(View.INVISIBLE);
//        resNum.setVisibility(View.INVISIBLE);
//        bresNum.setVisibility(View.INVISIBLE);
        cash!!.setText("1000")
        resCash = 0
        seekBar!!.setMax(1000)
        var tempCard = ""
        var check = 0
        for (i in 0..12) {
            tempCard = ""
            if (i < 9) {
                tempCard += (i + 2).toString()
            } else {
                if (i == 9) tempCard += "j"
                if (i == 10) tempCard += "q"
                if (i == 11) tempCard += "k"
                if (i == 12) tempCard += "a"
            }
            for (j in 0..3) {
                if (j == 0) cards[check] = tempCard + "_of_clubs"
                if (j == 1) cards[check] = tempCard + "_of_diamonds"
                if (j == 2) cards[check] = tempCard + "_of_hearts"
                if (j == 3) cards[check] = tempCard + "_of_spades"
                check++
            }
        }
        winLose!!.setText("Сделайте ставку:")
        seekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                sbRes!!.setText(i.toString())
                resCash = i
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                button!!.setVisibility(View.VISIBLE)
            }
        })
        button!!.setOnClickListener(View.OnClickListener {
            seekBar!!.setVisibility(View.INVISIBLE)
            sbRes!!.setVisibility(View.INVISIBLE)
            button!!.setVisibility(View.INVISIBLE)
            cash!!.setText((Integer.valueOf(cash!!.getText().toString()) - resCash).toString())
            pCard_1!!.setImageResource(0)
            pCard_2!!.setImageResource(0)
            pCard_3!!.setImageResource(0)
            pCard_4!!.setImageResource(0)
            check34 = 3
            bCard_1!!.setImageResource(0)
            bCard_2!!.setImageResource(0)
            bCard_3!!.setImageResource(0)
            bCard_4!!.setImageResource(0)
            backCard_4!!.setImageResource(R.drawable.card_back)

//                String id = "z2_diamonds";
//                int resId = getResources().getIdentifier(id, "drawable", getPackageName());
//                pCard_1.setImageResource(resId);
            var botRes = 0
            var playerRes = 0
            val cardsIngame = cards.clone()
            val r = Random()
            val b1 = r.nextInt(52) //первая карта бот
            bot!!.setText(cardsIngame[b1])
            botRes += cardToInt(cardsIngame[b1])
            bresNum!!.setText(Integer.toString(botRes)) //счет бота
            winLose!!.setText("")
            bCard_1!!.setImageResource(cardToId(cardsIngame[b1]))
            cardSound!!.start()
            bCard_1!!.startAnimation(animB)
            cardsIngame[b1] = ""
            var p1 = r.nextInt(52)
            var p2 = r.nextInt(52)
            var bj_p1: String? = "c"
            var bj_p2: String? = "c"
            var key1 = true
            var res: String? = ""
            while (key1) //первая карта игрок
            {
                if (cardsIngame[p1] == "") p1 = r.nextInt(52) else {
                    res = cardsIngame[p1]
                    bj_p1 = cardsIngame[p1]
                    key1 = false
                }
            }
            playerRes += cardToInt(cardsIngame[p1])
            pCard_1!!.setImageResource(cardToId(cardsIngame[p1]))
            cardSound!!.start()
            pCard_1!!.startAnimation(anim)
            cardsIngame[p1] = ""
            key1 = true
            while (key1) //вторая карта игрок
            {
                if (cardsIngame[p2] == "") p2 = r.nextInt(52) else {
                    res += cardsIngame[p2]
                    bj_p2 = cardsIngame[p2]
                    key1 = false
                }
            }
            playerRes += cardToInt(cardsIngame[p2])
            pCard_2!!.setImageResource(cardToId(cardsIngame[p2]))
            cardSound!!.start()
            pCard_2!!.startAnimation(anim)
            cardsIngame[p2] = ""
            next!!.setVisibility(View.VISIBLE)
            stop!!.setVisibility(View.VISIBLE)
            result!!.setText(res)
            if (bjCheck(bj_p1, bj_p2)) {
                resNum!!.setText("BJ")
                winLose!!.setText("Блэкджек! \n\n Сделайте ставку:")
                cash!!.setText((resCash * 3 + Integer.valueOf(cash!!.getText().toString())).toString())
                seekBar!!.setMax(Integer.valueOf(cash!!.getText().toString()))
                next!!.setVisibility(View.INVISIBLE)
                stop!!.setVisibility(View.INVISIBLE)
                button!!.setVisibility(View.INVISIBLE)
                sbRes!!.setVisibility(View.VISIBLE)
                seekBar!!.setVisibility(View.VISIBLE)
                seekBar!!.setMax(Integer.valueOf(cash!!.getText().toString()))
            } else resNum!!.setText(Integer.toString(playerRes))
            next!!.setOnClickListener(View.OnClickListener {
                var p3 = r.nextInt(52)
                var key1 = true
                while (key1) //первая карта игрок
                {
                    if (cardsIngame[p3] == "") p3 = r.nextInt(52) else {
                        result!!.append(cardsIngame[p3])
                        if (check34 == 3) {
                            pCard_3!!.setImageResource(cardToId(cardsIngame[p3]))
                            cardSound!!.start()
                            pCard_3!!.startAnimation(anim)
                        }
                        if (check34 == 4) {
                            pCard_4!!.setImageResource(cardToId(cardsIngame[p3]))
                            cardSound!!.start()
                            pCard_4!!.startAnimation(anim)
                        }
                        check34++
                        key1 = false
                    }
                }
                val playerRes = Integer.valueOf(resNum!!.getText().toString()) + cardToInt(cardsIngame[p3])
                resNum!!.setText(Integer.toString(playerRes))
                if (playerRes > 21) {
                    winLose!!.setText("Победа дилера \n\n Сделайте ставку:")
                    next!!.setVisibility(View.INVISIBLE)
                    stop!!.setVisibility(View.INVISIBLE)
                    button!!.setVisibility(View.INVISIBLE)
                    sbRes!!.setVisibility(View.VISIBLE)
                    seekBar!!.setVisibility(View.VISIBLE)
                    if (Integer.valueOf(cash!!.getText().toString()) == 0) cash!!.setText("1000")
                    seekBar!!.setMax(Integer.valueOf(cash!!.getText().toString()))
                }
                cardsIngame[p3] = ""
            })
            stop!!.setOnClickListener(View.OnClickListener {
                next!!.setVisibility(View.INVISIBLE)
                var botRes = Integer.valueOf(bresNum!!.getText().toString())
                var botCheck = 2
                var bot_bj1 = ""
                val handler = android.os.Handler()
                while (botRes < 17) {
                    var b2 = r.nextInt(52)
                    var key1 = true
                    while (key1) //первая карта игрок
                    {
                        if (cardsIngame[b2] == "") b2 = r.nextInt(52) else {
                            bot_bj1 = bot!!.getText().toString()
                            bot!!.append(cardsIngame[b2])
                            if (botCheck == 2) {
                                bCard_2!!.setImageResource(cardToId(cardsIngame[b2]))
                                cardSound!!.start()
                                bCard_2!!.startAnimation(animB)

                            }
                            if (botCheck == 3) {
                                bCard_3!!.setImageResource(cardToId(cardsIngame[b2]))
                                cardSound!!.start()
                                bCard_3!!.startAnimation(animB)
                            }
                            if (botCheck == 4) {
                                bCard_4!!.setImageResource(cardToId(cardsIngame[b2]))
                                cardSound!!.start()
                                bCard_4!!.startAnimation(animB)
                            }
                            if (bjCheck(bot_bj1, cardsIngame[b2])) {
                                botRes += 1
                            }
                            key1 = false
                            botCheck++
                        }
                    }
                    botRes += cardToInt(cardsIngame[b2])
                    bresNum!!.setText(Integer.toString(botRes))
                    cardsIngame[b2] = ""
                }
                stop!!.setVisibility(View.INVISIBLE)
                if ((botRes > Integer.valueOf(resNum!!.getText().toString())) and (botRes < 22)) {
                    winLose!!.setText("Победа дилераa \n\n Сделайте ставку:")
                } else if (botRes == Integer.valueOf(resNum!!.getText().toString())) {
                    winLose!!.setText("Возврат ставки \n\n Сделайте ставку:")
                    cash!!.setText((resCash + Integer.valueOf(cash!!.getText().toString())).toString())
                    seekBar!!.setMax(Integer.valueOf(cash!!.getText().toString()))
                } else {
                    cash!!.setText((resCash * 2 + Integer.valueOf(cash!!.getText().toString())).toString())
                    seekBar!!.setMax(Integer.valueOf(cash!!.getText().toString()))
                    winLose!!.setText("Победа игрока \n\n Сделайте ставку:")
                }
                if (Integer.valueOf(cash!!.getText().toString()) == 0) cash!!.setText("1000")
                button!!.setVisibility(View.INVISIBLE)
                sbRes!!.setVisibility(View.VISIBLE)
                seekBar!!.setVisibility(View.VISIBLE)
                seekBar!!.setMax(Integer.valueOf(cash!!.getText().toString()))
            })
        })
    }
}