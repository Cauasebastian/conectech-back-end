import './style.css'

const ParteSecundaria = () => {
    return(
        <div className='container'>
            <span className='content'>
                <span className='n_1'>Crie</span>
                <span className='n_2'>conexões</span>
                <span className='n_3'>com</span>
                <span className='n_4'>pessoas</span>
                <span className='n_5'>da</span>
                <span className='n_6'>tecnologia</span>
            </span>
            <div className='itens-list'>
                
                <div className='card-list'>
                    <img src="../../../public/images/img-groups.png" alt="" />
                    <h3 className='titulo-card'>Grupos</h3>
                    <p>Crie vínculos</p>
                </div>
                <div className='card-list'>
                    <img src="../../../public/images/img-foruns.png" alt="" />
                    <h3 className='titulo-card'>Fóruns</h3>
                    <p>Discuta sobre temas</p>
                </div>
                <div className='card-list'>
                    <img src="../../../public/images/img-events.png" alt="" />
                    <h3 className='titulo-card'>Eventos</h3>
                    <p>Participe de eventos</p>
                </div>
            </div>
        </div>
    )
}

export default ParteSecundaria