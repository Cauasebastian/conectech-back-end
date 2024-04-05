import { BrowserRouter, Route, Routes } from 'react-router-dom'
import TelaInicial from './pages/TelaInicial/telainicial'
import Home from './pages/Home/home'
import TelaLogin from './pages/TelaLogin/telalogin'
import TelaCadastro from './pages/TelaCadastro/index'

function App() {
  

  return (
    
        <BrowserRouter>
          <Routes>
            <Route path='' element={<TelaInicial/>}/>
            <Route path='/home' element={<Home/>}/>
            <Route path='/login' element={<TelaLogin/>}/>
            <Route path='/cadastro' element={<TelaCadastro/>}/>
          </Routes>
        </BrowserRouter>
    
   
  )
}

export default App
