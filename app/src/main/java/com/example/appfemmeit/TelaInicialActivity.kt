package com.example.appfemmeit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tela_cadastro.*
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaInicialActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this

    private var perfils = listOf<Perfil>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)


        //setando valores
        var args = intent.extras
        val nome = args?.getString("nome")

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tela Incial"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ConfiguraMenuLateral()

        recyclerPerfil?.layoutManager = LinearLayoutManager(context)
        recyclerPerfil?.itemAnimator = DefaultItemAnimator()
        recyclerPerfil?.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        taskPerfil()
    }

    fun taskPerfil(){

        perfils = PerfilService.getPerfil(context)
        recyclerPerfil?.adapter = PerfilAdapter(perfils) {onClickPerfils(it)}
    }


    fun onClickPerfils(perfil: Perfil){
        Toast.makeText(context, "Clicou em ${perfil.nome}", Toast.LENGTH_SHORT).show()

    }

    //funcao Botão Sair
    fun clickSair() {
        val returnIntent = Intent()
        returnIntent.putExtra("result", "Bye App FemmeIt")
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main, menu)
        // vincular evento de buscar
        (menu?.findItem(R.id.action_buscar)?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                Toast.makeText(context, newText, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

         if (id == R.id.action_adiconar) {
            Toast.makeText(this, "Clicou em Adiconar", Toast.LENGTH_LONG).show()
            //entra na tela de cadastro
          var intent = Intent(this , TelaCadastroActivity::class.java)
          startActivity(intent)
         } else if (id == R.id.action_atualizar) {
             Toast.makeText(this, "Clicou em atualizar", Toast.LENGTH_LONG).show()
             /*atualizando.visibility = View.VISIBLE
             Handler().postDelayed({
                 atualizando.setVisibility(View.INVISIBLE)
             }, 10000)*/
        } else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun ConfiguraMenuLateral(){
        var toggle = ActionBarDrawerToggle(
            this,
            layoutMenuLateral,
            toolbar,
            R.string.nav_pen,
            R.string.nav_close
        )
        layoutMenuLateral.addDrawerListener(toggle)
        toggle.syncState()

        menu_lateral.setNavigationItemSelectedListener(
            this
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_perfil ->{
                Toast.makeText(this, "Clicou em perfil", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_chat ->{
                Toast.makeText(this, "Clicou em CHAT", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_forum ->{
                Toast.makeText(this, "Clicou em Forum", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_localizacao -> {
                Toast.makeText(this, "Clicou em Localização", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_config ->{
                Toast.makeText(this, "Direcionando para Configuração", Toast.LENGTH_SHORT).show()
                var intent = Intent(this , TelaConfigActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sairApp ->{
                Toast.makeText(this, "Ate logo", Toast.LENGTH_SHORT).show()
                clickSair()
            }
        }
        layoutMenuLateral.closeDrawer(GravityCompat.START)
        return true
    }
}
