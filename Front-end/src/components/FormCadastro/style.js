import styled from "styled-components";

export const TituloCadastro = styled.h2`
     color: #f3f3f3;
    font-family: "Poppins", sans-serif;
`
export const ImgLogo = styled.img`
    max-width: 7rem;
    height: 7rem;
    margin-bottom: 3rem;
    
       
`
export const ParagrafoLogin = styled.p`
    color: #f3f3f3;
    font-size: 14px;
    font-family: "Poppins", sans-serif;
    
`
export const LinkLogin = styled.a`
    text-decoration: underline;
    color: #0B7FBE;
    font-family: "Poppins", sans-serif;
    cursor: pointer;
`

export const stylesFormCadastro = {
    div: {
        textAlign:'center',
        display:'flex',
        justifyContent:'center',
        alignItems:'center',
        flexDirection:'column',
        height:'100%',
        marginBottom: '3rem',
       
        '@media (min-width:1500px)':{
             marginTop: '1rem',
             
        }
    },
    form: {
        '& > :not(style)': { m: 1, width: '100ch' },
        display:'flex',
        flexDirection:'column',
        justifyContent:'center',
        alignItems:'center',
        width: '80%',
        
        border:'1px solid #f3f3f3',
        
        borderRadius:'10px',
        background: ' #7c7c7c59',
        boxShadow: '0 8px 32px 0 #1f26875e',
        backdropFilter: 'blur( 13.5px )',
        WebkitBackdropFilter: 'blur( 13.5px )',
        
        
        '@media (min-width:1500px)':{
            padding:'3rem'
        }
    },
    
    inputDiv:{
       marginBottom:'2rem',
        '& label.Mui-focused': {
            color:'#fff',
        }
    },
    inputLabel: {
        color:'#f3f3f3',
        fontFamily: 'Poppins' 
    
    },
    inputField:{
        color: '#f3f3f3',
        fontFamily: 'Poppins' ,
        '&.MuiOutlinedInput-root':{
            '& fieldset':{
                borderColor: '#f3f3f3'
            },
            '&:hover fieldset': {
                borderColor: '#B2BAC2', // cor cinza claro para hover
              },
              '&.Mui-focused fieldset': {
                borderColor: '#f3f3f3', // cor quando o input estiver ativo
              },
        }
    },
    inputIcon:{
        color: '#f3f3f3',
    },
    mensagemErro:{
        color: '#f44336'
    }
}