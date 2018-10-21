" No recursive mapping of keys to colemak (! applies to commands)

no e f
no! e f
no E F
no! E F
no r p
no! r p
no R P
no! R P
no t g
no! t g
no T G
no! T G
no y j
no! y j
no Y J
no! Y J
no u l
no! u l
no U L
no! U L
no i u
no! i u
no I U
no! I U
no o y
no! o y
no O Y
no! O Y
no p ;
no! p ;
no P :
no! P :

no s r
no! s r
no S R
no! S R
no d s
no! d s
no D S
no! D S
no f t
no! f t
no F T
no! F T
no g d
no! g d
no G D
no! G D
no j n
no! j n
no J N
no! J N
no k e
no! k e
no K E
no! K E
no l i
no! l i
no L I
no! L I
no ; o
no! ; o
no : O
no! : O

no n k
no! n k
no N K
no! N K


" Common ctrl + key
no <C-g> <C-d>
no <C-i> <C-u>


" Disable all kepmaps and return to normal
" :call Disable()
function Disable()
    mapc
    mapc!
endfunction
