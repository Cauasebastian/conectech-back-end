import { TelaInicialDiv } from './style'
import Header from '../../components/header'
import PartePrincipal from '../../components/PartePrincipalInicial'
import ParteSecundaria from '../../components/ParteSecInicial'
import Footer from '../../components/Footer'
 import {useNavigate} from 'react-router-dom'
import { ItemHeader } from '../../components/header/style'
import Botao from '../../components/Botao'

const TelaInicial = ()=> {
     const navigate = useNavigate()
     const goToLoginPage = () => {
         navigate('/login')
     }
     const goToTelaCadastro = () => {
        navigate('/cadastro')
     }
  
    return(
        <TelaInicialDiv>
            <Header position='fixed'>
                <ItemHeader onClick={goToLoginPage}>Entrar</ItemHeader>
                <ItemHeader >Como funciona?</ItemHeader>
                <Botao onClick={goToTelaCadastro}   nome="Vamos começar" link=""/>
            </Header>
            <PartePrincipal/>
            <ParteSecundaria/>
            <Footer/>
        </TelaInicialDiv>
    )
}

export default TelaInicial