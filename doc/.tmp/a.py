with open("api.md", "r", encoding="utf-8") as f:
    lines = f.readlines()
    for index, line in enumerate(lines):
        if line.startswith("## "):
            api = lines[index : index + 6]
            p1 = ("### " + " ".join(api[0].split(" ")[2:])).strip()
            p2 = "- 接口路径：" + api[2].strip()
            p3 = "- 接口描述：" + api[5].strip()
            p4 = "- 序列图："
            print(p1 + "\n" + p2 + "\n" + p3 + "\n" + p4 + "\n")
