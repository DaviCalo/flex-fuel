# FlexFuel - Álcool ou Gasolina?

Este é um aplicativo Android simples, desenvolvido em **Jetpack Compose**, que ajuda os motoristas a decidir se é mais vantajoso abastecer com Álcool ou Gasolina com base nos preços atuais.

Este projeto foi desenvolvido como parte da atividade acadêmica **AT02 - Álcool ou Gasolina? - Simples**.

---

## 📱 Telas (Screenshots)

O aplicativo conta com um **tema claro** e um **tema escuro totalmente personalizado**, que se adapta às configurações do sistema operacional.

| Modo Claro (Padrão) | Modo Escuro (Customizado) |
|:-------------------:|:-------------------------:|
| ![claro](https://github.com/user-attachments/assets/cb7a13b3-2b7c-41a0-b58b-d6450befa5a0) | ![escuro](https://github.com/user-attachments/assets/6cde4c79-b917-4906-bce2-9e9a1464b977) |
| *Tema padrão do Material 3.* | *Tema customizado.* |

---

## ✨ Features Principais

* **Cálculo de Custo-Benefício:** O app determina o combustível mais econômico com base no preço do álcool e da gasolina inseridos.
* **Seletor de Proporção:** Permite ao usuário alternar facilmente entre os cálculos de **70%** e **75%** de rendimento do álcool em relação à gasolina, usando um componente `Switch`.
* **Tema Dinâmico (Claro e Escuro):** Interface com um tema claro padrão e um tema escuro customizado com uma paleta de cores em tons de azul, que se adapta automaticamente às configurações do sistema.
* **Ícone Personalizado:** O aplicativo possui um ícone único, diferenciando-o de outros projetos.
* **Preservação de Estado:** A escolha do percentual (70% ou 75%) e os valores nos campos de texto são mantidos mesmo após a rotação da tela ou outras reconfigurações, graças ao uso do **ViewModel** e **StateFlow**.
* **Layout Moderno Edge-to-Edge:** A interface do aplicativo se estende por toda a tela, desenhando atrás das barras de status e navegação do sistema para uma experiência imersiva.

---

## 🚀 Como Usar

1.  Abra o aplicativo.
2.  No campo "**Álcool (preço por litro)**", insira o valor do álcool.
3.  No campo "**Gasolina (preço por litro)**", insira o valor da gasolina.
4.  Use o seletor para escolher entre a proporção de **70%** ou **75%**.
5.  Clique no botão "**Calcular**".
6.  Um diálogo aparecerá na tela informando qual dos dois combustíveis é a opção mais vantajosa.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Kotlin
* **Interface Gráfica:** Jetpack Compose
* **Componentes Visuais:** Material Design 3
* **Gerenciamento de Estado:** ViewModel com StateFlow
* **Arquitetura:** MVVM (Model-View-ViewModel)
