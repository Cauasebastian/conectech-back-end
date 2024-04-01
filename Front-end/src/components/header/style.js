import styled from "styled-components"

export const HeaderDiv = styled.header`
    width: 100%;
    padding: 1.2rem;
    font-family: "Poppins", sans-serif;
    display: flex;
    justify-content: space-around;
    align-items: center;
    position:${props => props.position} ;
    z-index: 1000;
    background-color: #003262;
    
    
`

export const ImagemLogo = styled.img`
    width: 11rem;
    cursor: pointer;
`

export const ListaItensHeader = styled.div`
    display: flex;
    gap: 6rem;
    align-items: center;

    @media (max-width: 900px){
        gap: 2rem;
    }
    
`

export const ItemHeader = styled.a`
    cursor: pointer;
    color: #e1e1e1;
    white-space: nowrap;

    @media(max-width: 686px){
        display: none;
    }
    

    &:hover{
        color: #fff;
        
        
    }
`

export const BotaoHamburguer = styled.button`
    width: 2.5rem;
    height: 2.5rem;
    font-size: 1.5rem;
    align-items: flex-end;
    justify-content: center;
    border: none;
    background-color: transparent;
    line-height: 0;
    cursor: pointer;
    transition: all 0.4s ease;
    display: none;
    
`

