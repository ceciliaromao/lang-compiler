.class public iterVarArr
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    invokestatic iterVarArr/_main()V
    return
.end method

.method public static _main()V
    .limit stack 10
    .limit locals 10
    ldc 10
    newarray int
    astore 0
    iconst_0
    istore 1
L0:
    iload 1
    ldc 10
    if_icmpge L1
    aload 0
    iload 1
    ldc 2
    iload 1
    imul
    iastore
    iinc 1 1
    goto L0
L1:
    aload 0
    astore 1
    iconst_0
    istore 2
L2:
    iload 2
    aload 1
    arraylength
    if_icmpge L3
    aload 1
    iload 2
    iaload
    istore 3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 3
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 32
    invokevirtual java/io/PrintStream/print(C)V
    iinc 2 1
    goto L2
L3:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 46
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method


