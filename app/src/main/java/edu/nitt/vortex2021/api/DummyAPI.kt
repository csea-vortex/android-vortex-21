package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.Hint
import edu.nitt.vortex2021.model.Question
import edu.nitt.vortex2021.model.UserProgress

fun getUserProgress(): UserProgress {
    return UserProgress(1, 2)
}

fun getQuestions(roundNumber: Int): List<Question> {
    val questions = mutableListOf<Question>()
    val hints1 = mutableListOf<Hint>()
    hints1.add(Hint("TEXT", "", "answer is apple"))
    hints1.add(Hint("IMAGE", "https://i2.wp.com/ceklog.kindel.com/wp-content/uploads/2013/02/firefox_2018-07-10_07-50-11.png", ""))
    hints1.add(Hint("IMAGE", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1200px-Apple_logo_black.svg.png", ""))
    val additionalHint1 = Hint("TEXT", "", "type apple")
    val question1 = Question(roundNumber, 1, hints1, additionalHint1,"apple", false)
    questions.add(question1)

    val hints2 = mutableListOf<Hint>()
    hints2.add(Hint("IMAGE", "https://cdn.mos.cms.futurecdn.net/42E9as7NaTaAi4A6JcuFwG-1200-80.jpg", ""))
    hints2.add(Hint("TEXT", "", "answer is banana"))
    hints2.add(Hint("IMAGE", "https://post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/02/271157_2200-732x549.jpg", ""))
    val additionalHint2 = Hint("TEXT", "", "type banana")
    val question2 = Question(roundNumber, 1, hints2, additionalHint2,"banana", false)
    questions.add(question2)

    val hints3 = mutableListOf<Hint>()
    hints3.add(Hint("IMAGE", "https://www.jessicagavin.com/wp-content/uploads/2019/02/carrots-7-1200.jpg", ""))
    hints3.add(Hint("TEXT", "", "answer is carrot"))
    hints3.add(Hint("IMAGE", "https://foodandnutrition.org/wp-content/uploads/Savor-Carrots-780x520.jpg", ""))
    val additionalHint3 = Hint("TEXT", "", "type carrot")
    val question3 = Question(roundNumber, 1, hints3, additionalHint3,"carrot", false)
    questions.add(question3)

    val hints4 = mutableListOf<Hint>()
    hints4.add(Hint("IMAGE", "https://c.ndtvimg.com/2020-03/u54eq30o_dates_625x300_19_March_20.jpg", ""))
    hints4.add(Hint("IMAGE", "https://www.deccanherald.com/sites/dh/files/article_images/2020/05/19/dates-3579610_1920-1584457158-1559049813.jpg", ""))
    hints4.add(Hint("TEXT", "", "answer is dates"))
    val additionalHint4 = Hint("TEXT", "", "type dates")
    val question4 = Question(roundNumber, 1, hints4, additionalHint4,"dates", false)
    questions.add(question4)

    val hints5 = mutableListOf<Hint>()
    hints5.add(Hint("IMAGE", "https://health.clevelandclinic.org/wp-content/uploads/sites/3/2020/09/CC_HE_Eldberberry.jpg", ""))
    hints5.add(Hint("TEXT", "", "answer is elderberry"))
    hints5.add(Hint("IMAGE", "https://www.naturehills.com/media/catalog/product/cache/35c1080e597d6a74b42d0d88ced836c1/y/o/york-elderberry-fruit-anzd-foliage-close-600x600.jpg", ""))
    val additionalHint5 = Hint("TEXT", "", "type elderberry")
    val question5 = Question(roundNumber, 1, hints5, additionalHint5,"elderberry", false)
    questions.add(question5)
    return questions
}