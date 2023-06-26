package com.example.vibees.screens.home.myparties

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vibees.Api.APIInterface
import com.example.vibees.Models.Party
import com.example.vibees.Models.User
import com.example.vibees.screens.user.Header
import com.example.vibees.ui.theme.GrayWhite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPartiesScreen(
    onClick: (id: String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical=25.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        var attendingParties by remember { mutableStateOf(listOf<Party>()) }
        var hostingParties by remember { mutableStateOf(listOf<Party>()) }
        // fetch all parties from endpoint /parties
        val apiService = APIInterface()

        // Parties being attended by the user
        val callResponseAttending = apiService.getMyPartiesAttending(User("5bdfc21f-ea15-43b3-9654-093f15d63ba7"))
        val responseAttending = callResponseAttending.enqueue(
            object: Callback<List<Party>> {
                override fun onResponse(
                    call: Call<List<Party>>,
                    response: Response<List<Party>>
                ) {
                    Log.d("TAG", "success")
                    attendingParties = response.body()!!
                    Log.d("TAG", parties.toString())
                }

                override fun onFailure(call: Call<List<Party>>, t: Throwable) {
                    Log.d("TAG", "FAILURE")
                    Log.d("TAG", t.printStackTrace().toString())
                }
            }
        )

        // Parties being hosted by the user
        val callResponseHosting = apiService.getMyPartiesHosting(User("5bdfc21f-ea15-43b3-9654-093f15d63ba7"))
        val responseHosting = callResponseHosting.enqueue(
            object: Callback<List<Party>> {
                override fun onResponse(
                    call: Call<List<Party>>,
                    response: Response<List<Party>>
                ) {
                    Log.d("TAG", "success")
                    hostingParties = response.body()!!
                    Log.d("TAG", parties.toString())
                }

                override fun onFailure(call: Call<List<Party>>, t: Throwable) {
                    Log.d("TAG", "FAILURE")
                    Log.d("TAG", t.printStackTrace().toString())
                }
            }
        )

        // Header
        Header(firstLine = "MY", secondLine = "PARTIES")

        // search bar
        var searchText by remember { mutableStateOf("")}
        var searchActive by remember { mutableStateOf(false)}

        SearchBar(
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = {
                searchActive = false

            },
            active = searchActive,
            colors = SearchBarDefaults.colors(containerColor = GrayWhite),
            onActiveChange = {
                searchActive = it
            },
            placeholder = {
                Text(text = "Search parties")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                if (searchActive and searchText.isNotEmpty()) {
                    IconButton(onClick = {
                        searchText = ""
                    }, content = {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
                    })
                } else if (searchActive) {
                    IconButton(onClick = {
                        searchActive = false
                    }, content = {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Up Icon")
                    })
                }
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {

        }
        
        // parties
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            contentPadding = PaddingValues(horizontal = 5.dp, vertical = 2.dp),
        ) {
            item {
                Text(
                    text = "Hosted by Me",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                )
            }
            items(attendingParties.size) {
                PartyItem(partyinfo = attendingParties[it], isMyParty = true, onClick = onClick)
            }
            item {
                Text(
                    text = "Upcoming Parties",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(15.dp)
                )
            }
            items(hostingParties.size) {
                PartyItem(partyinfo = hostingParties[it], isMyParty = true, onClick = onClick,)
            }
        }
    }
}