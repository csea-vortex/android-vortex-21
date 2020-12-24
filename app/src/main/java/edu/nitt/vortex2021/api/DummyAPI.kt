package edu.nitt.vortex2021.api

import edu.nitt.vortex2021.model.Hint
import edu.nitt.vortex2021.model.LatestLinkedQuestion

fun getDummyQuestion(): LatestLinkedQuestion {
    return LatestLinkedQuestion(
            false,
            false,
            "title",
            arrayListOf(Hint("hint", ""), Hint("youtube", "https://i.pinimg.com/originals/31/23/9a/31239a2f70e4f8e4e3263fafb00ace1c.png"), Hint("", "https://picsum.photos/400/300")),
            false,
            Hint("Additional Hint", ""),
            2,
            10,
            3,
            20,
            arrayListOf("First Answer", "Second answer", "Third answer")
    )
}