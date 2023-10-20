import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perfect.prodsuit.Model.AreaListModel
import com.perfect.prodsuit.Model.CustomerBalanceModel
import com.perfect.prodsuit.Repository.AreaListRepository
import com.perfect.prodsuit.Repository.CustomerBalanceRepository

class CustomerBalanceViewModel : ViewModel(){

    var customerbalancelistdata: MutableLiveData<CustomerBalanceModel>? = null

    fun getCustombal(context: Context,TicketDate : String) : LiveData<CustomerBalanceModel>? {
        customerbalancelistdata = CustomerBalanceRepository.getServicesApiCall(context,TicketDate)
        return customerbalancelistdata
    }

}