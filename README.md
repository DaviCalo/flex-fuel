# FlexFuel - Álcool ou Gasolina?

Este é um aplicativo Android simples, desenvolvido em **Jetpack Compose**, que ajuda os motoristas a decidir se é mais vantajoso abastecer com Álcool ou Gasolina com base nos preços atuais.

Este projeto foi desenvolvido como parte da atividade acadêmica **AT02 - Álcool ou Gasolina? - Simples**.

---

## 📱 Telas (Screenshots)

O aplicativo conta com duas telas principais. Tela Inicial, Tela de Adicionar Posto, Tela de Editar Posto.

| Tela Inicial | Tela de Adicionar Posto | Tela de Editar Posto |
|:-------------------:|:-------------------------:|:-------------------------:|
| <img width="720" height="1600" alt="Screenshot_20251120_223602" src="https://github.com/user-attachments/assets/f5d3b302-349b-4a80-8a6f-c72efbd80d8f" /> | <img width="720" height="1600" alt="Screenshot_20251120_223623" src="https://github.com/user-attachments/assets/a5101bf1-8d4c-4ecd-bef3-0097f6633ccd" /> | <img width="720" height="1600" alt="Screenshot_20251120_223647" src="https://github.com/user-attachments/assets/18a69aa3-dd14-4a12-b40d-3fbbf458ad5d" /> |

---

## ✨ Features Principais

* **Cálculo de Custo-Benefício:** O app determina o combustível mais econômico com base no preço do álcool e da gasolina inseridos.
* **Seletor de Proporção:** Permite ao usuário alternar facilmente entre os cálculos de **70%** e **75%** de rendimento do álcool em relação à gasolina, usando um componente `Switch`.
* **Tema Dinâmico (Claro e Escuro):** Interface com um tema claro padrão e um tema escuro customizado com uma paleta de cores em tons de azul, que se adapta automaticamente às configurações do sistema.
* **Ícone Personalizado:** O aplicativo possui um ícone único, diferenciando-o de outros projetos.
* **Preservação de Estado:** A escolha do percentual (70% ou 75%) e os valores nos campos de texto são mantidos mesmo após a rotação da tela ou outras reconfigurações, graças ao uso do **ViewModel** e **StateFlow**.
* **Layout Moderno Edge-to-Edge:** A interface do aplicativo se estende por toda a tela, desenhando atrás das barras de status e navegação do sistema para uma experiência imersiva.

---

## Relatório de Implementação - FlexFuel

Abaixo descrevo como cada requisito da avaliação foi atendido tecnicamente no código fonte do projeto

---

### 1. Salvar e Restaurar o Estado do Switch (0,5 ponto)
**Requisito:** O aplicativo deve ser capaz de salvar o estado do switch (Álcool ou Gasolina/Proporção) e restaurá-lo.

**Implementação:**
O estado é gerenciado de forma reativa no `ViewModel` e persistido dentro do objeto `Post` ao salvar. Na tela de criação, o switch reflete o valor do `StateFlow`.

* **Arquivo:** [CreateDataViewModel.kt](https://github.com/DaviCalo/flex-fuel/blob/main/app/src/main/java/com/smd/flexfuel/viewmodel/CreateDataViewModel.kt)
    ```kotlin
    // Gerenciamento de estado reativo
    private val _isRatio70 = MutableStateFlow(false)
    val isRatio70: StateFlow<Boolean> = _isRatio70.asStateFlow()

    fun onRatioChange(newRatio: Boolean) {
        _isRatio70.update { newRatio }
    }
    ```

---

### 2. CRUD de Valores de Combustível (2,5 pontos)
**Requisito:** Salvar, editar e excluir valores usando `SharedPreferences` e JSON.

**Implementação:**
Foi criada a classe `SharedPrefsManager` que utiliza a biblioteca **Gson** para serializar a lista de objetos `Post` em uma String JSON, permitindo o armazenamento complexo no SharedPreferences.

* **Arquivo:** [SharedPrefsManager.kt](https://github.com/DaviCalo/flex-fuel/blob/main/app/src/main/java/com/smd/flexfuel/utils/SharedPrefsManager.kt)
    ```kotlin
    // Serialização e Persistência (Create/Update)
    fun includePost(newPost: Post) {
        val currentList = getPostList().toMutableList()
        currentList.add(newPost)
        val updatedJson = gson.toJson(currentList)
        prefs.edit { putString(KEY_POSTS, updatedJson) }
    }

    // Leitura e Deserialização (Read)
    fun getPostList(): ArrayList<Post> {
        val savedJson = prefs.getString(KEY_POSTS, null)
        // ...Converte JSON String de volta para List<Post>...
        val list: List<Post> = gson.fromJson(savedJson, type)
        return ArrayList(list)
    }

    // Exclusão (Delete)
    fun deletePost(id: Int) {
        // Remove item da lista e salva o JSON atualizado
        val updatedJson = gson.toJson(currentList)
        prefs.edit { putString(KEY_POSTS, updatedJson) }
    }
    ```

---

### 3. Exibição de Lista de Postos (3 pontos)
**Requisito:** Exibir lista com nome e valores, permitindo seleção para ver detalhes.

**Implementação:**
Utilização do componente `LazyColumn` do Jetpack Compose para renderização eficiente da lista. O item da lista é representado pelo `CardPostComponent`.

* **Arquivo:** [MainScreen.kt](https://github.com/DaviCalo/flex-fuel/blob/main/app/src/main/java/com/smd/flexfuel/ui/screens/MainScreen.kt)
    ```kotlin
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = postos, key = { it.id }) { posto ->
            CardPostComponent(
                post = posto,
                // Navegação passando o ID do posto para edição/detalhes
                onClick = { navController.navigate("editdatascreen/${posto.id}") },
                // Callback para o clique no mapa
                onMapClick = { /* Lógica do mapa */ }
            )
        }
    }
    ```

---

### 4. Acesso à Localização e Mapa (2 pontos)
**Requisito:** Permissão de localização, salvar coordenadas e exibir no mapa via Intent.

**Implementação:**
O app solicita permissões (`ACCESS_FINE_LOCATION`) em tempo de execução. Se concedido, usa `FusedLocationProviderClient` para capturar a posição atual. Para visualização, usa uma **Implicit Intent** com o esquema `geo:`.

* **Captura:** [CreateDataScreen.kt](https://github.com/DaviCalo/flex-fuel/blob/main/app/src/main/java/com/smd/flexfuel/ui/screens/CreateDataScreen.kt)
    ```kotlin
    // Solicitação de permissão e captura
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            viewModel.updateLocation(location.latitude, location.longitude)
        }
    }
    ```

* **Exibição:** [MainScreen.kt](https://github.com/DaviCalo/flex-fuel/blob/main/app/src/main/java/com/smd/flexfuel/ui/screens/MainScreen.kt)
    ```kotlin
    // Intent para abrir aplicativo de mapa externo
    val gmmIntentUri = Uri.parse("geo:${lat},${long}?q=${lat},${long}(${Uri.encode(name)})")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
    ```

---

### 5. Suporte a Internacionalização (2 pontos)
**Requisito:** Suporte a Português e Inglês.

**Implementação:**
O código não utiliza strings literais (hardcoded). Todos os textos de interface são chamados via `stringResource(R.string.id)`, permitindo que o Android selecione automaticamente o arquivo `strings.xml` correto (pasta `values` ou `values-pt`) baseado na configuração do dispositivo.

* **Arquivos:** [values-pt-rBR](https://github.com/DaviCalo/flex-fuel/tree/main/app/src/main/res/values-pt-rBR), [values-en](https://github.com/DaviCalo/flex-fuel/blob/main/app/src/main/res/values-en/strings.xml)

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Kotlin
* **Interface Gráfica:** Jetpack Compose
* **Componentes Visuais:** Material Design 3
* **Gerenciamento de Estado:** ViewModel com StateFlow
* **Arquitetura:** MVVM (Model-View-ViewModel)
