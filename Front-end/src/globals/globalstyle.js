import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
    body{
        padding: 0;
        margin: 0;
        box-sizing: border-box;
        overflow-x: hidden;
        height: 100vh;
        width: 100vw;
        display: flex;
        flex-direction: column;
        
        

    

    
    } 

    li{
        list-style: none;
    }
`

export default GlobalStyle