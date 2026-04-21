#!/bin/zsh

# 1. 解析具名參數
for arg in "$@"; do
  case $arg in
    version=*)
      VERSION="${arg#*=}"  # 這裡的 #*= 代表去掉等號左邊的字串
      ;;
  esac
done

if [ -z "$VERSION" ]; then
    echo "❌ ERROR: Please give the version no.！"
    echo "HELP：./push.sh version=0.0.2 service=payment-service"
    exit 1
fi

# 3. 執行流程
echo "🚀 Pushing image to ghcr with version：$VERSION"

# 登入 (假設 PAT 已存在環境變數)
echo $PAT | docker login ghcr.io -u RayYau999 --password-stdin

SERVICE_NAME=("login-service" "payment-service" "eshop-api-gateway")

for s in "${SERVICE_NAME[@]}"; do
  echo "正在處理：$s"
  docker tag ${s}:local ghcr.io/rayyau999/${s}:${VERSION}
  docker push ghcr.io/rayyau999/${s}:${VERSION}
done

echo "✅ Succeed！"