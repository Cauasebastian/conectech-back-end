import Form from '../../components/Form'
import Header from '../../components/header'
import { DivLogin } from './style'


const TelaLogin = () => {
    const itensHeader = [
          
    ]
    return(
        <DivLogin>
            <Header  itens={itensHeader} />
            <Form/>
        </DivLogin>
    )
}

export default TelaLogin