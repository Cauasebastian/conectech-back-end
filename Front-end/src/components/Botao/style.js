import styled from "styled-components";

export const ItemBotao = styled.button`
    padding: ${props => props.padding || "0.8rem 4rem"};
    border: none;
    border-radius: 20px;
    background-color: #0B7FBE;
    cursor: pointer;
    display: ${props => props.display};
`
export const TextoBotao = styled.a`
    text-decoration: none;
    font-family: "Poppins", sans-serif;
    color: #FFFFFF;
    white-space: nowrap;
   
`