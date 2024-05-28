import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.ticketapp.R

data class TicketViewData(
    val eventName: String,
    val eventImageResId: Int,
    val barcodeImageResId: Int,
    val eventDate: String,
    val eventTime: String,
    val eventLocation: String
)

class TicketViewPreviewProvider : PreviewParameterProvider<TicketViewData> {
    override val values: Sequence<TicketViewData> = sequenceOf(
        TicketViewData(
            eventName = "Les Ardentes",
            eventImageResId = R.drawable.visu_festival_les_ardentes,
            barcodeImageResId = R.drawable.master,
            eventDate = "19 juin 2024",
            eventTime = "11:00 - 20:00",
            eventLocation = "Lieu exemple"
        )
    )
}