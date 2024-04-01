import { TelaInicialDiv } from './style'
import Header from '../../components/header'
import PartePrincipal from '../../components/PartePrincipalInicial'
import ParteSecundaria from '../../components/ParteSecInicial'
import Footer from '../../components/Footer'
 import {useNavigate} from 'react-router-dom'

const TelaInicial = ()=> {
     const navigate = useNavigate()
     const goToLoginPage = () => {
         navigate('/login')
     }
    const itensHeader = [
        {id: 1, nome:"Como Funciona"},
        {id: 2, nome:"Entrar", caminho: goToLoginPage},
        
    ]
    return(
        <TelaInicialDiv>
            <Header position='fixed' itens={itensHeader}/>
            <PartePrincipal/>
            <ParteSecundaria/>
            <Footer/>
        </TelaInicialDiv>
    )
}

export default TelaInicial