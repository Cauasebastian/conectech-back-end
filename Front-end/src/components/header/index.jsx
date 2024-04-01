/* eslint-disable react/prop-types */
import { HeaderDiv, ImagemLogo, ListaItensHeader, ItemHeader, BotaoHamburguer } from './style'
import Botao from '../Botao'
import {useNavigate} from 'react-router-dom'
const Header = ({itens, displayBotao, position})=> {

    const navigate = useNavigate()
     const goToInitialPage = () => {
         navigate('/')
     }
    return(
        
             <HeaderDiv position={position}>
                <ImagemLogo onClick={goToInitialPage} src='../../../public/images/img-logo.png'/>

                <ListaItensHeader>
                    {itens.length> 0 && itens.map((item)=> <ItemHeader onClick={item.caminho} key={item.id}>{item.nome}</ItemHeader>)}
                    <Botao display={displayBotao}  nome="Vamos começar" link=""/>
                </ListaItensHeader>

                <BotaoHamburguer >
                    <img src='../../../public/images/img-menu.svg'/>
                </BotaoHamburguer>
            </HeaderDiv>
        
       
    )
}

export default Header