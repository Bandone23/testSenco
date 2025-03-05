package com.example.examplesenco.data.remote

import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.model.PokemonResponse
import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ApiServiceTest {

    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        apiService = mockk()
    }

    @Test
    fun `getPokemon returns successful response with Pokemon list`() = runTest {

        val pokemonItem1 = PokemonItem(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/")
        val pokemonItem2 = PokemonItem(name = "ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/")
        val expectedResponse = PokemonResponse(
            count = 1118,
            next = "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
            previous = null,
            results = listOf(pokemonItem1, pokemonItem2)
        )


        coEvery { apiService.getPokemon(offset = 0, limit = 20) } returns Response.success(expectedResponse)


        val result = apiService.getPokemon(offset = 0, limit = 20)


        assertTrue(result.isSuccessful)
        assertEquals(expectedResponse, result.body())
        assertEquals(2, result.body()?.results?.size)
        assertEquals("bulbasaur", result.body()?.results?.first()?.name)
    }

    @Test
    fun `getSearchPokemon returns correct Pokemon details`() = runTest {

        val pokemonName = "pikachu"
        val mockDetail = mockk<PokemonDetailResponse>(relaxed = true)

        coEvery { apiService.getSearchPokemon(pokemonName) } returns Response.success(mockDetail)


        val result = apiService.getSearchPokemon(pokemonName)


        assertTrue(result.isSuccessful)
        assertEquals(mockDetail, result.body())
    }

    @Test
    fun `getSearchPokemon returns error for nonexistent Pokemon`() = runTest {

        val nonExistentPokemon = "nonexistentpokemon123"


        val errorResponseBody = "Not Found".toResponseBody("application/json".toMediaTypeOrNull())

        val errorResponse = Response.error<PokemonDetailResponse>(
            404,
            errorResponseBody
        )

        coEvery { apiService.getSearchPokemon(nonExistentPokemon) } returns errorResponse


        val result = apiService.getSearchPokemon(nonExistentPokemon)


        assertFalse(result.isSuccessful)
        assertEquals(404, result.code())
    }
}