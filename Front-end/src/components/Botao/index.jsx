/* eslint-disable react/prop-types */
import { ItemBotao, TextoBotao } from "./style"



const Botao = ({nome, link, padding, onClick, display, type}) => {
    return(
        <ItemBotao type={type} display={display} onClick={onClick} padding={padding}>
            <TextoBotao  href={link}>{nome}</TextoBotao>
        </ItemBotao>
    )
}

export default Botao